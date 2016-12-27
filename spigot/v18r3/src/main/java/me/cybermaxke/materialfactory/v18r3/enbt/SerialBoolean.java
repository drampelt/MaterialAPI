package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTTagByte;

public class SerialBoolean implements EnbtSerializer<Boolean, NBTTagByte> {

    @Override
    public EnbtSerializerData<NBTTagByte> serialize(EnbtSerializerContext ctx, Boolean object) {
        return new EnbtSerializerData<>(new NBTTagByte((byte) (object ? 1 : 0)));
    }

    @Override
    public Boolean deserialize(EnbtSerializerContext ctx, EnbtSerializerData<NBTTagByte> tag) {
        return tag.getTag().f() != 0;
    }

}
