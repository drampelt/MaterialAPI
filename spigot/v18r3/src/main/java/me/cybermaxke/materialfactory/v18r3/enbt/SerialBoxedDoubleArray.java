package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagList;

public class SerialBoxedDoubleArray implements EnbtSerializer<Double[], NBTTagList> {

    @Override
    public EnbtSerializerData<NBTTagList> serialize(EnbtSerializerContext ctx, Double[] object) {
        NBTTagList tag = new NBTTagList();
        for (int i = 0; i < object.length; i++) {
            tag.add(new NBTTagDouble(object[i]));
        }
        return new EnbtSerializerData<>(tag);
    }

    @Override
    public Double[] deserialize(EnbtSerializerContext ctx, EnbtSerializerData<NBTTagList> tag) {
        Double[] object = new Double[tag.getTag().size()];
        for (int i = 0; i < object.length; i++) {
            object[i] = ((NBTTagDouble) tag.getTag().h(i)).asDouble();
        }
        return object;
    }

}
