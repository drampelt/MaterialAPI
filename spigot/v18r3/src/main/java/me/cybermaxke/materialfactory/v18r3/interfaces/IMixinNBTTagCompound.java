package me.cybermaxke.materialfactory.v18r3.interfaces;

import net.minecraft.server.v1_11_R1.NBTBase;

import java.util.Map;

public interface IMixinNBTTagCompound {

    Map<String, NBTBase> getBacking();
}
