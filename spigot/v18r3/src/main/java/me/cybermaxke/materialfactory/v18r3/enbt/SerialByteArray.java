package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTTagByteArray;

public class SerialByteArray implements EnbtSerializer<byte[], NBTTagByteArray> {

    @Override
    public EnbtSerializerData<NBTTagByteArray> serialize(EnbtSerializerContext ctx, byte[] object) {
        return new EnbtSerializerData<>(new NBTTagByteArray(object));
    }

    @Override
    public byte[] deserialize(EnbtSerializerContext ctx, EnbtSerializerData<NBTTagByteArray> tag) {
        return tag.getTag().c();
    }

}
