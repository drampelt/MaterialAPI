package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTBase;

import javax.annotation.Nullable;

public class EnbtSerializerData<N extends NBTBase> {

    private final N tag;
    @Nullable private final int[] extraData;

    public EnbtSerializerData(N tag, int... extraData) {
        this.extraData = extraData;
        this.tag = tag;
    }

    public N getTag() {
        return this.tag;
    }

    public int[] getExtraData() {
        return this.extraData;
    }
}
