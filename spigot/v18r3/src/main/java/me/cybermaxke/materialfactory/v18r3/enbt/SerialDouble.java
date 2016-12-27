package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTTagDouble;

public class SerialDouble implements EnbtSerializer<Double, NBTTagDouble> {

    @Override
    public EnbtSerializerData<NBTTagDouble> serialize(EnbtSerializerContext ctx, Double object) {
        return new EnbtSerializerData<>(new NBTTagDouble(object));
    }

    @Override
    public Double deserialize(EnbtSerializerContext ctx, EnbtSerializerData<NBTTagDouble> tag) {
        return tag.getTag().asDouble();
    }

}
