package me.illia.trollmod;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;

public class SoundUtil {
	public static class AudioData implements Comparable<AudioData> {
		public ByteBuffer data;
		public int channels;
		public int sampleRate;
		public int bitsPerSample;

		public void writeTo(PacketByteBuf buf) {
			ByteBuffer src = data.duplicate();
			src.rewind();

			buf.writeVarInt(src.remaining());  // 🔹 write length first
			buf.writeBytes(src);
//			buf.writeBytes(data);

			buf.writeVarInt(channels);
			buf.writeVarInt(sampleRate);
			buf.writeVarInt(bitsPerSample);
		}

		public static Path getPath(BlockPos pos, MinecraftServer server) {
			Path savePath = server.getSavePath(WorldSavePath.ROOT).resolve(Trollmod.MODID + "/audio");
			try {
				Trollmod.LOGGER.info("savePath: " + savePath);
				MessageDigest digest = MessageDigest.getInstance("SHA-256");

				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();

				digest.update((byte)(x >> 24));
				digest.update((byte)(x >> 16));
				digest.update((byte)(x >> 8));
				digest.update((byte)x);

				digest.update((byte)(y >> 24));
				digest.update((byte)(y >> 16));
				digest.update((byte)(y >> 8));
				digest.update((byte)y);

				digest.update((byte)(z >> 24));
				digest.update((byte)(z >> 16));
				digest.update((byte)(z >> 8));
				digest.update((byte)z);

				byte[] hash = digest.digest();
				String hex = HexFormat.of().formatHex(hash);

				return savePath.resolve(hex + ".tad");
			} catch (NoSuchAlgorithmException e) {
				throw new CrashException(CrashReport.create(e, "Failed to write audio to file"));
			}
		}

		// ONLY CALL THIS ON SERVER!!!!
		public void writeToFile(BlockPos pos, MinecraftServer server) {
			ByteBuffer src = data.duplicate();
			src.rewind();

			Path file = getPath(pos, server);
			try {
				Trollmod.LOGGER.info("file: " + file);
				Files.createDirectories(file.getParent());
				Files.deleteIfExists(file);
				Files.createFile(file);

				DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.toFile()));

				dos.writeInt(channels);
				dos.writeInt(sampleRate);
				dos.writeInt(bitsPerSample);

				dos.writeInt(src.remaining());

				byte[] dataBytes = new byte[src.remaining()];
				src.get(dataBytes);

				for (byte dataByte : dataBytes)
					dos.writeByte(dataByte);
			} catch (Throwable e) {
				throw new CrashException(CrashReport.create(e, "Failed to write audio data to file"));
			}
		}

		public static AudioData readFromFile(BlockPos pos, MinecraftServer server) {
			AudioData audioData = new AudioData();

			Path file = getPath(pos, server);

			if (!Files.exists(file)) return null;

			try {
				DataInputStream dis = new DataInputStream(new FileInputStream(file.toFile()));

				audioData.channels = dis.readInt();
				audioData.sampleRate = dis.readInt();
				audioData.bitsPerSample = dis.readInt();

				int len = dis.readInt();
				byte[] dataBytes = new byte[len];
				for (int i = 0; i < len; i++) {
					byte dataByte = dis.readByte();
					dataBytes[i] = dataByte;
				}

				ByteBuffer data = ByteBuffer.allocateDirect(len);
				data.put(dataBytes).flip();

				audioData.data = data;
			} catch (Throwable e) {
				CrashReport.create(e, "Failed to read audio data from file");
			}

			return audioData;
		}

		@Override
		public String toString() {
			return "data: " + data +
				", channels=" + channels +
				", sampleRate=" + sampleRate +
				", bitsPerSample=" + bitsPerSample;
		}

		public static AudioData readFrom(PacketByteBuf buf) {
			int len = buf.readVarInt();

			byte[] bytes = new byte[len];
			buf.readBytes(bytes);
			ByteBuffer data = ByteBuffer.allocateDirect(len);
			data.put(bytes).flip();


			int channels = buf.readVarInt();
			int sampleRate = buf.readVarInt();
			int bitsPerSample = buf.readVarInt();

			AudioData audioData = new AudioData();

			audioData.data = data;
			audioData.channels = channels;
			audioData.sampleRate = sampleRate;
			audioData.bitsPerSample = bitsPerSample;

			return audioData;
		}

		@Override
		public int compareTo(@NotNull SoundUtil.AudioData other) {
			return Integer.compare(this.data.limit(), other.data.limit());
		}
	}

	public static AudioData loadSound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream stream = AudioSystem.getAudioInputStream(new File(path));
		AudioFormat format = stream.getFormat();

		// Convert to 16-bit PCM if not already
		if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
			format = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				format.getSampleRate(),
				16,
				format.getChannels(),
				format.getChannels() * 2,
				format.getSampleRate(),
				false
			);
			stream = AudioSystem.getAudioInputStream(format, stream);
		}

		byte[] bytes = stream.readAllBytes();
		ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
		buffer.put(bytes).flip();

		AudioData data = new AudioData();
		data.data = buffer;
		data.channels = format.getChannels();
		data.sampleRate = (int) format.getSampleRate();
		data.bitsPerSample = format.getSampleSizeInBits();

		return data;
	}

	public static void play(SoundUtil.AudioData audioData) {
		ALC.create();

		int buffer = AL10.alGenBuffers();
		int source = AL10.alGenSources();

		int format;
		if (audioData.channels == 1) {
			format = audioData.bitsPerSample == 16 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_MONO8;
		} else if (audioData.channels == 2) {
			format = audioData.bitsPerSample == 16 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_STEREO8;
		} else {
			throw new IllegalArgumentException("Unsupported channel count: " + audioData.channels);
		}

		audioData.data.rewind();
		AL10.alBufferData(buffer, format, audioData.data, audioData.sampleRate);

		AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(source);

		int state;
		do {
			state = AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE);
			try {
				Thread.sleep(10);
			} catch (InterruptedException ignored) {}
		} while (state == AL10.AL_PLAYING);

		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
		ALC.destroy();
	}
}