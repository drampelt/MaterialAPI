package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagLong;

public class SerialLongArray implements EnbtSerializer<long[], NBTTagList> {

    @Override
    public EnbtSerializerData<NBTTagList> serialize(EnbtSerializerContext ctx, long[] object) {
        NBTTagList tag = new NBTTagList();
        for (int i = 0; i < object.length; i++) {
            tag.add(new NBTTagLong(object[i]));
        }
        return new EnbtSerializerData<>(tag);
    }

    @Override
    public long[] deserialize(EnbtSerializerContext ctx, EnbtSerializerData<NBTTagList> tag) {
        long[] object = new long[tag.getTag().size()];
        for (int i = 0; i < object.length; i++) {
            object[i] = ((NBTTagLong) tag.getTag().h(i)).d();
        }
        return object;
    }

}
