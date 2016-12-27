package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTTagInt;

public class SerialInteger implements EnbtSerializer<Integer, NBTTagInt> {

    @Override
    public EnbtSerializerData<NBTTagInt> serialize(EnbtSerializerContext ctx, Integer object) {
        return new EnbtSerializerData<>(new NBTTagInt(object));
    }

    @Override
    public Integer deserialize(EnbtSerializerContext ctx, EnbtSerializerData<NBTTagInt> tag) {
        return tag.getTag().e();
    }
}
