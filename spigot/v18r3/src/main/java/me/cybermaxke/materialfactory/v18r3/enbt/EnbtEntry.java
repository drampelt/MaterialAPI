package me.cybermaxke.materialfactory.v18r3.enbt;

import net.minecraft.server.v1_11_R1.NBTBase;

import javax.annotation.Nullable;

public final class EnbtEntry {

    private final NBTBase tag;
    @Nullable private final Integer type;
    private final int[] extraData;

    public EnbtEntry(NBTBase tag, @Nullable Integer type, int... extraData) {
        this.extraData = extraData;
        this.type = type;
        this.tag = tag;
    }

    public int[] getExtraData() {
        return this.extraData;
    }

    public NBTBase getTag() {
        return this.tag;
    }

    public int getType() {
        return this.type == null ? this.tag.getTypeId() : this.type;
    }

    @Nullable
    public Integer getExtendedType() {
        return this.type;
    }
}
