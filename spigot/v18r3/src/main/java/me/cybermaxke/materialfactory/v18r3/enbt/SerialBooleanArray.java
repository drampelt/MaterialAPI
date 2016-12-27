package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTTagByteArray;

public class SerialBooleanArray implements EnbtSerializer<boolean[], NBTTagByteArray> {

    @Override
    public EnbtSerializerData<NBTTagByteArray> serialize(EnbtSerializerContext ctx, boolean[] object) {
        byte[] array = new byte[object.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) (object[i] ? 1 : 0);
        }
        return new EnbtSerializerData<>(new NBTTagByteArray(array));
    }

    @Override
    public boolean[] deserialize(EnbtSerializerContext ctx, EnbtSerializerData<NBTTagByteArray> tag) {
        byte[] array = tag.getTag().c();
        boolean[] object = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            object[i] = array[i] != 0;
        }
        return object;
    }

}
