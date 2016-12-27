/**
 * 
 * This software is part of the TagUtils
 * 
 * TagUtils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 *  
 * TagUtils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TagUtils. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.tagutils;

import net.minecraft.server.v1_11_R1.*;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TagUtils {

	@SuppressWarnings("unchecked")
	private static Tag<?> createTag(NBTBase tag) {
		byte d = tag.getTypeId();
		
		switch (d) {
			case 0:
				return new TagEnd();
			case 1:
				return new TagByte(((NBTTagByte) tag).g());
			case 2:
				return new TagShort(((NBTTagShort) tag).f());
			case 3:
				return new TagInteger(((NBTTagInt) tag).e());
			case 4:
				return new TagLong(((NBTTagLong) tag).d());
			case 5:
				return new TagFloat(((NBTTagFloat) tag).i());
			case 6:
				return new TagDouble(((NBTTagDouble) tag).asDouble());
			case 7:
				return new TagByteArray(((NBTTagByteArray) tag).c());
			case 8:
				return new TagString(((NBTTagString) tag).c_());
			case 9:
				List<Tag<?>> tags = new ArrayList<Tag<?>>();

				try {
					Field f = NBTTagList.class.getDeclaredField("list");
					f.setAccessible(true);
					List<NBTBase> list = (List<NBTBase>) f.get(tag);
					for (NBTBase b : list) {
						tags.add(createTag(b));
					}
				} catch (Exception e1) {}

				return new TagList(tags);
			case 10:
				TagCompound c = new TagCompound();

				try {
					Field f2 = NBTTagCompound.class.getDeclaredField("map");
					f2.setAccessible(true);
					Map<String, NBTBase> map = (Map<String, NBTBase>) f2.get(tag);

					for (Entry<String, NBTBase> en : map.entrySet()) {
						c.set(en.getKey(), createTag(en.getValue()));
					}
				} catch (Exception e) {}

				return c;
			case 11:
				return new TagIntegerArray(((NBTTagIntArray) tag).d());
			default:
				return null;
		}
	}

	private static NBTBase createTag(Tag<?> tag) {
		byte d = tag.getTypeId();

		switch (d) {
			case 0:
				return createEndTag();
			case 1:
				return new NBTTagByte((Byte) tag.getValue());
			case 2:
				return new NBTTagShort((Short) tag.getValue());
			case 3:
				return new NBTTagInt((Integer) tag.getValue());
			case 4:
				return new NBTTagLong((Long) tag.getValue());
			case 5:
				return new NBTTagFloat((Float) tag.getValue());
			case 6:
				return new NBTTagDouble((Double) tag.getValue());
			case 7:
				return new NBTTagByteArray((byte[]) tag.getValue());
			case 8:
				return new NBTTagString((String) tag.getValue());
			case 9:
				NBTTagList l = new NBTTagList();
				for (Tag<?> t : ((TagList) tag).getValue()) {
					l.add(createTag((Tag<?>) t));
				}
				return l;
			case 10:
				NBTTagCompound c = new NBTTagCompound();
				for (Entry<String, Tag<?>> en : ((TagCompound) tag).entrySet()) {
					c.set(en.getKey(), createTag((Tag<?>) en.getValue()));
				}
				return c;
			case 11:
				return new NBTTagIntArray((int[]) tag.getValue());
			default:
				return null;
		}
	}

	private static NBTTagEnd createEndTag() {
		Constructor constructor = NBTTagEnd.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		try {
			return (NBTTagEnd) constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
		    return null;
		}
	}

	public static boolean save(File target, Tag<?> tag) {
		if (target.isDirectory()) {
			return false;
		}

		if (!target.exists()) {
			try {
				target.createNewFile();
			} catch (Exception e) {
				return false;
			}
		}

		try {
			FileOutputStream fos = new FileOutputStream(target);
			DataOutputStream dos = new DataOutputStream(fos);
//			NBTBase.a(createTag(tag), dos);
            Method method = NBTBase.class.getDeclaredMethod("write", DataOutput.class);
            method.invoke(createTag(tag), dos);
			dos.close();
			fos.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static Tag<?> load(File target) {
		if (!target.exists() || target.isDirectory()) {
			return null;
		}

		try {		
			FileInputStream fis = new FileInputStream(target);
			DataInputStream dis = new DataInputStream(fis);
			NBTBase t = createEmptyTag();
			Method method = NBTBase.class.getDeclaredMethod("load", DataInput.class, int.class, NBTReadLimiter.class);
			dis.close();
			fis.close();
			return createTag(t);
		} catch (Exception e) {}

		return null;
	}

	private static NBTBase createEmptyTag() {
		try {
			Method method = NBTBase.class.getDeclaredMethod("createTag", byte.class);
			return (NBTBase) method.invoke(null);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
		    return null;
		}
	}

	public static <T extends Tag<?>> T load(Class<T> clazz, File target) {
		return clazz.cast(load(target));
	}

	public static TagCompound getItemStackAsTag(ItemStack itemstack) {
		net.minecraft.server.v1_11_R1.ItemStack is = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound c = is.save(new NBTTagCompound());
		return (TagCompound) createTag(c);
	}

	public static ItemStack getItemStackFromTag(TagCompound tag) {
		NBTTagCompound c = (NBTTagCompound) createTag(tag);
//		return CraftItemStack.asCraftMirror(net.minecraft.server.v1_11_R1.ItemStack.createStack(c));
        return null;
	}

	public static TagCompound getInventoryAsTag(Inventory inventory) {
		TagCompound t = new TagCompound();
		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack is = inventory.getItem(i);
			if (is != null) {
				t.setCompound("Slot" + i, getItemStackAsTag(is));
			}
		}
		return t;
	}

	public static Inventory getInventoryFromTag(Inventory inventory, TagCompound tag) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (tag.hasKey("Slot" + i)) {
				inventory.setItem(i, getItemStackFromTag(tag.getCompound("Slot" + i)));
			}
		}
		return inventory;
	}

	public static TagCompound getTag(ItemStack itemstack) {
		net.minecraft.server.v1_11_R1.ItemStack is = CraftItemStack.asNMSCopy(itemstack);
		if (is == null) {
			return null;
		}
		try {
			Field field = net.minecraft.server.v1_11_R1.ItemStack.class.getDeclaredField("tag");
			field.setAccessible(true);
			NBTTagCompound t = (NBTTagCompound) field.get(is);
			return (TagCompound) (t == null ? null : createTag(t));
		} catch (NoSuchFieldException | IllegalAccessException e) {
			return null;
		}
	}

	public static ItemStack setTag(ItemStack itemstack, TagCompound tag) {
		net.minecraft.server.v1_11_R1.ItemStack is = CraftItemStack.asNMSCopy(itemstack);
		if (is == null) {
			return itemstack;
		}
		try {
			Field field = net.minecraft.server.v1_11_R1.ItemStack.class.getDeclaredField("tag");
			field.setAccessible(true);
			field.set(is, tag == null ? null : (NBTTagCompound) createTag(tag));
		} catch (NoSuchFieldException | IllegalAccessException e) {
		    return itemstack;
		}
		return CraftItemStack.asCraftMirror(is);
	}

	public static void saveInventory(Inventory inventory, File target) {
		save(target, getInventoryAsTag(inventory));
	}

	public static Inventory loadInventory(Inventory inventory, File target) {
		return getInventoryFromTag(inventory, load(TagCompound.class, target));
	}
}