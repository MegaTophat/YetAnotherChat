package me.mega.yetanotherchat.packet;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class PacketDataSerializer extends ByteBuf {
    private final ByteBuf delegate;

    public PacketDataSerializer(final ByteBuf delegate) {
        this.delegate = delegate;
    }

    public String readString(final int maxLength) {
        final int stringLength = this.readInt();

        if (stringLength > maxLength) {
            throw new DecoderException("Received String too long!");
        }
        if (stringLength < 0) {
            throw new DecoderException("Received String has negative length?");
        }

        final String decodedString = new String(this.readBytes(stringLength).array(), Charsets.UTF_8);

        if (decodedString.length() > maxLength) {
            throw new DecoderException("Received String too long!");
        }

        return decodedString;
    }

    public void writeString(final String string) {
        final byte[] stringBytes = string.getBytes(StandardCharsets.UTF_8);

        if (stringBytes.length > Short.MAX_VALUE) {
            throw new EncoderException("Sending String is too big!");
        }

        this.writeInt(stringBytes.length);
        this.writeBytes(stringBytes);
    }

    @Override
    public int capacity() {
        return delegate.capacity();
    }

    @Override
    public ByteBuf capacity(final int newCapacity) {
        return delegate.capacity(newCapacity);
    }

    @Override
    public int maxCapacity() {
        return delegate.maxCapacity();
    }

    @Override
    public ByteBufAllocator alloc() {
        return delegate.alloc();
    }

    /**
     * @deprecated use the Little Endian accessors,
     * e.g. getShortLE, getIntLE instead of creating a buffer with swapped endianness.
     */
    @Override
    @Deprecated
    public ByteOrder order() {
        return delegate.order();
    }

    /**
     * @deprecated use the Little Endian accessors,
     * e.g. getShortLE, getIntLE instead of creating a buffer with swapped endianness.
     */
    @Override
    @Deprecated
    public ByteBuf order(final ByteOrder endianness) {
        return delegate.order(endianness);
    }

    @Override
    public ByteBuf unwrap() {
        return delegate.unwrap();
    }

    @Override
    public boolean isDirect() {
        return delegate.isDirect();
    }

    @Override
    public boolean isReadOnly() {
        return delegate.isReadOnly();
    }

    @Override
    public ByteBuf asReadOnly() {
        return delegate.asReadOnly();
    }

    @Override
    public int readerIndex() {
        return delegate.readerIndex();
    }

    @Override
    public ByteBuf readerIndex(final int readerIndex) {
        return delegate.readerIndex(readerIndex);
    }

    @Override
    public int writerIndex() {
        return delegate.writerIndex();
    }

    @Override
    public ByteBuf writerIndex(final int writerIndex) {
        return delegate.writerIndex(writerIndex);
    }

    @Override
    public ByteBuf setIndex(final int readerIndex, final int writerIndex) {
        return delegate.setIndex(readerIndex, writerIndex);
    }

    @Override
    public int readableBytes() {
        return delegate.readableBytes();
    }

    @Override
    public int writableBytes() {
        return delegate.writableBytes();
    }

    @Override
    public int maxWritableBytes() {
        return delegate.maxWritableBytes();
    }

    @Override
    public int maxFastWritableBytes() {
        return delegate.maxFastWritableBytes();
    }

    @Override
    public boolean isReadable() {
        return delegate.isReadable();
    }

    @Override
    public boolean isReadable(final int size) {
        return delegate.isReadable(size);
    }

    @Override
    public boolean isWritable() {
        return delegate.isWritable();
    }

    @Override
    public boolean isWritable(final int size) {
        return delegate.isWritable(size);
    }

    @Override
    public ByteBuf clear() {
        return delegate.clear();
    }

    @Override
    public ByteBuf markReaderIndex() {
        return delegate.markReaderIndex();
    }

    @Override
    public ByteBuf resetReaderIndex() {
        return delegate.resetReaderIndex();
    }

    @Override
    public ByteBuf markWriterIndex() {
        return delegate.markWriterIndex();
    }

    @Override
    public ByteBuf resetWriterIndex() {
        return delegate.resetWriterIndex();
    }

    @Override
    public ByteBuf discardReadBytes() {
        return delegate.discardReadBytes();
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        return delegate.discardSomeReadBytes();
    }

    @Override
    public ByteBuf ensureWritable(final int minWritableBytes) {
        return delegate.ensureWritable(minWritableBytes);
    }

    @Override
    public int ensureWritable(final int minWritableBytes, final boolean force) {
        return delegate.ensureWritable(minWritableBytes, force);
    }

    @Override
    public boolean getBoolean(final int index) {
        return delegate.getBoolean(index);
    }

    @Override
    public byte getByte(final int index) {
        return delegate.getByte(index);
    }

    @Override
    public short getUnsignedByte(final int index) {
        return delegate.getUnsignedByte(index);
    }

    @Override
    public short getShort(final int index) {
        return delegate.getShort(index);
    }

    @Override
    public short getShortLE(final int index) {
        return delegate.getShortLE(index);
    }

    @Override
    public int getUnsignedShort(final int index) {
        return delegate.getUnsignedShort(index);
    }

    @Override
    public int getUnsignedShortLE(final int index) {
        return delegate.getUnsignedShortLE(index);
    }

    @Override
    public int getMedium(final int index) {
        return delegate.getMedium(index);
    }

    @Override
    public int getMediumLE(final int index) {
        return delegate.getMediumLE(index);
    }

    @Override
    public int getUnsignedMedium(final int index) {
        return delegate.getUnsignedMedium(index);
    }

    @Override
    public int getUnsignedMediumLE(final int index) {
        return delegate.getUnsignedMediumLE(index);
    }

    @Override
    public int getInt(final int index) {
        return delegate.getInt(index);
    }

    @Override
    public int getIntLE(final int index) {
        return delegate.getIntLE(index);
    }

    @Override
    public long getUnsignedInt(final int index) {
        return delegate.getUnsignedInt(index);
    }

    @Override
    public long getUnsignedIntLE(final int index) {
        return delegate.getUnsignedIntLE(index);
    }

    @Override
    public long getLong(final int index) {
        return delegate.getLong(index);
    }

    @Override
    public long getLongLE(final int index) {
        return delegate.getLongLE(index);
    }

    @Override
    public char getChar(final int index) {
        return delegate.getChar(index);
    }

    @Override
    public float getFloat(final int index) {
        return delegate.getFloat(index);
    }

    @Override
    public float getFloatLE(final int index) {
        return delegate.getFloatLE(index);
    }

    @Override
    public double getDouble(final int index) {
        return delegate.getDouble(index);
    }

    @Override
    public double getDoubleLE(final int index) {
        return delegate.getDoubleLE(index);
    }

    @Override
    public ByteBuf getBytes(final int index, final ByteBuf dst) {
        return delegate.getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(final int index, final ByteBuf dst, final int length) {
        return delegate.getBytes(index, dst, length);
    }

    @Override
    public ByteBuf getBytes(final int index, final ByteBuf dst, final int dstIndex, final int length) {
        return delegate.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(final int index, final byte[] dst) {
        return delegate.getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(final int index, final byte[] dst, final int dstIndex, final int length) {
        return delegate.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(final int index, final ByteBuffer dst) {
        return delegate.getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(final int index, final OutputStream out, final int length) throws IOException {
        return delegate.getBytes(index, out, length);
    }

    @Override
    public int getBytes(final int index, final GatheringByteChannel out, final int length) throws IOException {
        return delegate.getBytes(index, out, length);
    }

    @Override
    public int getBytes(final int index, final FileChannel out, final long position, final int length) throws IOException {
        return delegate.getBytes(index, out, position, length);
    }

    @Override
    public CharSequence getCharSequence(final int index, final int length, final Charset charset) {
        return delegate.getCharSequence(index, length, charset);
    }

    @Override
    public ByteBuf setBoolean(final int index, final boolean value) {
        return delegate.setBoolean(index, value);
    }

    @Override
    public ByteBuf setByte(final int index, final int value) {
        return delegate.setByte(index, value);
    }

    @Override
    public ByteBuf setShort(final int index, final int value) {
        return delegate.setShort(index, value);
    }

    @Override
    public ByteBuf setShortLE(final int index, final int value) {
        return delegate.setShortLE(index, value);
    }

    @Override
    public ByteBuf setMedium(final int index, final int value) {
        return delegate.setMedium(index, value);
    }

    @Override
    public ByteBuf setMediumLE(final int index, final int value) {
        return delegate.setMediumLE(index, value);
    }

    @Override
    public ByteBuf setInt(final int index, final int value) {
        return delegate.setInt(index, value);
    }

    @Override
    public ByteBuf setIntLE(final int index, final int value) {
        return delegate.setIntLE(index, value);
    }

    @Override
    public ByteBuf setLong(final int index, final long value) {
        return delegate.setLong(index, value);
    }

    @Override
    public ByteBuf setLongLE(final int index, final long value) {
        return delegate.setLongLE(index, value);
    }

    @Override
    public ByteBuf setChar(final int index, final int value) {
        return delegate.setChar(index, value);
    }

    @Override
    public ByteBuf setFloat(final int index, final float value) {
        return delegate.setFloat(index, value);
    }

    @Override
    public ByteBuf setFloatLE(final int index, final float value) {
        return delegate.setFloatLE(index, value);
    }

    @Override
    public ByteBuf setDouble(final int index, final double value) {
        return delegate.setDouble(index, value);
    }

    @Override
    public ByteBuf setDoubleLE(final int index, final double value) {
        return delegate.setDoubleLE(index, value);
    }

    @Override
    public ByteBuf setBytes(final int index, final ByteBuf src) {
        return delegate.setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(final int index, final ByteBuf src, final int length) {
        return delegate.setBytes(index, src, length);
    }

    @Override
    public ByteBuf setBytes(final int index, final ByteBuf src, final int srcIndex, final int length) {
        return delegate.setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(final int index, final byte[] src) {
        return delegate.setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(final int index, final byte[] src, final int srcIndex, final int length) {
        return delegate.setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(final int index, final ByteBuffer src) {
        return delegate.setBytes(index, src);
    }

    @Override
    public int setBytes(final int index, final InputStream in, final int length) throws IOException {
        return delegate.setBytes(index, in, length);
    }

    @Override
    public int setBytes(final int index, final ScatteringByteChannel in, final int length) throws IOException {
        return delegate.setBytes(index, in, length);
    }

    @Override
    public int setBytes(final int index, final FileChannel in, final long position, final int length) throws IOException {
        return delegate.setBytes(index, in, position, length);
    }

    @Override
    public ByteBuf setZero(final int index, final int length) {
        return delegate.setZero(index, length);
    }

    @Override
    public int setCharSequence(final int index, final CharSequence sequence, final Charset charset) {
        return delegate.setCharSequence(index, sequence, charset);
    }

    @Override
    public boolean readBoolean() {
        return delegate.readBoolean();
    }

    @Override
    public byte readByte() {
        return delegate.readByte();
    }

    @Override
    public short readUnsignedByte() {
        return delegate.readUnsignedByte();
    }

    @Override
    public short readShort() {
        return delegate.readShort();
    }

    @Override
    public short readShortLE() {
        return delegate.readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        return delegate.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return delegate.readUnsignedShortLE();
    }

    @Override
    public int readMedium() {
        return delegate.readMedium();
    }

    @Override
    public int readMediumLE() {
        return delegate.readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        return delegate.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return delegate.readUnsignedMediumLE();
    }

    @Override
    public int readInt() {
        return delegate.readInt();
    }

    @Override
    public int readIntLE() {
        return delegate.readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        return delegate.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return delegate.readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        return delegate.readLong();
    }

    @Override
    public long readLongLE() {
        return delegate.readLongLE();
    }

    @Override
    public char readChar() {
        return delegate.readChar();
    }

    @Override
    public float readFloat() {
        return delegate.readFloat();
    }

    @Override
    public float readFloatLE() {
        return delegate.readFloatLE();
    }

    @Override
    public double readDouble() {
        return delegate.readDouble();
    }

    @Override
    public double readDoubleLE() {
        return delegate.readDoubleLE();
    }

    @Override
    public ByteBuf readBytes(final int length) {
        return delegate.readBytes(length);
    }

    @Override
    public ByteBuf readSlice(final int length) {
        return delegate.readSlice(length);
    }

    @Override
    public ByteBuf readRetainedSlice(final int length) {
        return delegate.readRetainedSlice(length);
    }

    @Override
    public ByteBuf readBytes(final ByteBuf dst) {
        return delegate.readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(final ByteBuf dst, final int length) {
        return delegate.readBytes(dst, length);
    }

    @Override
    public ByteBuf readBytes(final ByteBuf dst, final int dstIndex, final int length) {
        return delegate.readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(final byte[] dst) {
        return delegate.readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(final byte[] dst, final int dstIndex, final int length) {
        return delegate.readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(final ByteBuffer dst) {
        return delegate.readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(final OutputStream out, final int length) throws IOException {
        return delegate.readBytes(out, length);
    }

    @Override
    public int readBytes(final GatheringByteChannel out, final int length) throws IOException {
        return delegate.readBytes(out, length);
    }

    @Override
    public CharSequence readCharSequence(final int length, final Charset charset) {
        return delegate.readCharSequence(length, charset);
    }

    @Override
    public int readBytes(final FileChannel out, final long position, final int length) throws IOException {
        return delegate.readBytes(out, position, length);
    }

    @Override
    public ByteBuf skipBytes(final int length) {
        return delegate.skipBytes(length);
    }

    @Override
    public ByteBuf writeBoolean(final boolean value) {
        return delegate.writeBoolean(value);
    }

    @Override
    public ByteBuf writeByte(final int value) {
        return delegate.writeByte(value);
    }

    @Override
    public ByteBuf writeShort(final int value) {
        return delegate.writeShort(value);
    }

    @Override
    public ByteBuf writeShortLE(final int value) {
        return delegate.writeShortLE(value);
    }

    @Override
    public ByteBuf writeMedium(final int value) {
        return delegate.writeMedium(value);
    }

    @Override
    public ByteBuf writeMediumLE(final int value) {
        return delegate.writeMediumLE(value);
    }

    @Override
    public ByteBuf writeInt(final int value) {
        return delegate.writeInt(value);
    }

    @Override
    public ByteBuf writeIntLE(final int value) {
        return delegate.writeIntLE(value);
    }

    @Override
    public ByteBuf writeLong(final long value) {
        return delegate.writeLong(value);
    }

    @Override
    public ByteBuf writeLongLE(final long value) {
        return delegate.writeLongLE(value);
    }

    @Override
    public ByteBuf writeChar(final int value) {
        return delegate.writeChar(value);
    }

    @Override
    public ByteBuf writeFloat(final float value) {
        return delegate.writeFloat(value);
    }

    @Override
    public ByteBuf writeFloatLE(final float value) {
        return delegate.writeFloatLE(value);
    }

    @Override
    public ByteBuf writeDouble(final double value) {
        return delegate.writeDouble(value);
    }

    @Override
    public ByteBuf writeDoubleLE(final double value) {
        return delegate.writeDoubleLE(value);
    }

    @Override
    public ByteBuf writeBytes(final ByteBuf src) {
        return delegate.writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(final ByteBuf src, final int length) {
        return delegate.writeBytes(src, length);
    }

    @Override
    public ByteBuf writeBytes(final ByteBuf src, final int srcIndex, final int length) {
        return delegate.writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(final byte[] src) {
        return delegate.writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(final byte[] src, final int srcIndex, final int length) {
        return delegate.writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(final ByteBuffer src) {
        return delegate.writeBytes(src);
    }

    @Override
    public int writeBytes(final InputStream in, final int length) throws IOException {
        return delegate.writeBytes(in, length);
    }

    @Override
    public int writeBytes(final ScatteringByteChannel in, final int length) throws IOException {
        return delegate.writeBytes(in, length);
    }

    @Override
    public int writeBytes(final FileChannel in, final long position, final int length) throws IOException {
        return delegate.writeBytes(in, position, length);
    }

    @Override
    public ByteBuf writeZero(final int length) {
        return delegate.writeZero(length);
    }

    @Override
    public int writeCharSequence(final CharSequence sequence, final Charset charset) {
        return delegate.writeCharSequence(sequence, charset);
    }

    @Override
    public int indexOf(final int fromIndex, final int toIndex, final byte value) {
        return delegate.indexOf(fromIndex, toIndex, value);
    }

    @Override
    public int bytesBefore(final byte value) {
        return delegate.bytesBefore(value);
    }

    @Override
    public int bytesBefore(final int length, final byte value) {
        return delegate.bytesBefore(length, value);
    }

    @Override
    public int bytesBefore(final int index, final int length, final byte value) {
        return delegate.bytesBefore(index, length, value);
    }

    @Override
    public int forEachByte(final ByteProcessor processor) {
        return delegate.forEachByte(processor);
    }

    @Override
    public int forEachByte(final int index, final int length, final ByteProcessor processor) {
        return delegate.forEachByte(index, length, processor);
    }

    @Override
    public int forEachByteDesc(final ByteProcessor processor) {
        return delegate.forEachByteDesc(processor);
    }

    @Override
    public int forEachByteDesc(final int index, final int length, final ByteProcessor processor) {
        return delegate.forEachByteDesc(index, length, processor);
    }

    @Override
    public ByteBuf copy() {
        return delegate.copy();
    }

    @Override
    public ByteBuf copy(final int index, final int length) {
        return delegate.copy(index, length);
    }

    @Override
    public ByteBuf slice() {
        return delegate.slice();
    }

    @Override
    public ByteBuf retainedSlice() {
        return delegate.retainedSlice();
    }

    @Override
    public ByteBuf slice(final int index, final int length) {
        return delegate.slice(index, length);
    }

    @Override
    public ByteBuf retainedSlice(final int index, final int length) {
        return delegate.retainedSlice(index, length);
    }

    @Override
    public ByteBuf duplicate() {
        return delegate.duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return delegate.retainedDuplicate();
    }

    @Override
    public int nioBufferCount() {
        return delegate.nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer() {
        return delegate.nioBuffer();
    }

    @Override
    public ByteBuffer nioBuffer(final int index, final int length) {
        return delegate.nioBuffer(index, length);
    }

    @Override
    public ByteBuffer internalNioBuffer(final int index, final int length) {
        return delegate.internalNioBuffer(index, length);
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return delegate.nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(final int index, final int length) {
        return delegate.nioBuffers(index, length);
    }

    @Override
    public boolean hasArray() {
        return delegate.hasArray();
    }

    @Override
    public byte[] array() {
        return delegate.array();
    }

    @Override
    public int arrayOffset() {
        return delegate.arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress() {
        return delegate.hasMemoryAddress();
    }

    @Override
    public long memoryAddress() {
        return delegate.memoryAddress();
    }

    @Override
    public boolean isContiguous() {
        return delegate.isContiguous();
    }

    @Override
    public ByteBuf asByteBuf() {
        return delegate.asByteBuf();
    }

    @Override
    public String toString(final Charset charset) {
        return delegate.toString(charset);
    }

    @Override
    public String toString(final int index, final int length, final Charset charset) {
        return delegate.toString(index, length, charset);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {

        return delegate.equals(obj);
    }

    @Override
    public int compareTo(final ByteBuf buffer) {
        return delegate.compareTo(buffer);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    @Override
    public ByteBuf retain(final int increment) {
        return delegate.retain(increment);
    }

    @Override
    public ByteBuf retain() {
        return delegate.retain();
    }

    @Override
    public ByteBuf touch() {
        return delegate.touch();
    }

    @Override
    public ByteBuf touch(final Object hint) {
        return delegate.touch(hint);
    }

    @Override
    public int refCnt() {
        return delegate.refCnt();
    }

    @Override
    public boolean release() {
        return delegate.release();
    }

    @Override
    public boolean release(final int decrement) {
        return delegate.release(decrement);
    }
}