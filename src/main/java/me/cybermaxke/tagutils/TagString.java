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

public final class TagString extends Tag<String> {

	public TagString(String value) {
		super(value);
	}

	@Override
	public String getTagName() {
		return "TAG_String";
	}

	@Override
	public byte getTypeId() {
		return 8;
	}

	@Override
	public TagString clone() {
		return new TagString(this.getValue());
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof TagString && ((TagString) other).getValue().equals(this.getValue());
	}
}