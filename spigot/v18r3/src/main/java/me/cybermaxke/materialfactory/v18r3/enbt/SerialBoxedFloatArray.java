package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTTagFloat;
import net.minecraft.server.v1_11_R1.NBTTagList;

public class SerialBoxedFloatArray implements EnbtSerializer<Float[], NBTTagList> {

    @Override
    public EnbtSerializerData<NBTTagList> serialize(EnbtSerializerContext ctx, Float[] object) {
        NBTTagList tag = new NBTTagList();
        for (int i = 0; i < object.length; i++) {
            tag.add(new NBTTagFloat(object[i]));
        }
        return new EnbtSerializerData<>(tag);
    }

    @Override
    public Float[] deserialize(EnbtSerializerContext ctx, EnbtSerializerData<NBTTagList> tag) {
        Float[] object = new Float[tag.getTag().size()];
        for (int i = 0; i < object.length; i++) {
            object[i] = ((NBTTagFloat) tag.getTag().h(i)).i();
        }
        return object;
    }

}
