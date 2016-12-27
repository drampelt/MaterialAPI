package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTTagString;

public class SerialString implements EnbtSerializer<String, NBTTagString> {

    @Override
    public EnbtSerializerData<NBTTagString> serialize(EnbtSerializerContext ctx, String object) {
        return new EnbtSerializerData<>(new NBTTagString(object));
    }

    @Override
    public String deserialize(EnbtSerializerContext ctx, EnbtSerializerData<NBTTagString> tag) {
        return tag.getTag().c_();
    }

}
