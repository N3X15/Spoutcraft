/*
 * This file is part of Spoutcraft (http://wiki.getspout.org/).
 * 
 * Spoutcraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Spoutcraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.getspout.spout.entity;

import org.spoutcraft.spoutcraftapi.entity.Projectile;

public abstract class AbstractProjectile extends CraftEntity implements Projectile {

	private boolean doesBounce;

	public AbstractProjectile(net.minecraft.src.Entity entity) {
		super(entity);
		doesBounce = false;
	}

	public boolean doesBounce() {
		return doesBounce;
	}

	public void setBounce(boolean doesBounce) {
		this.doesBounce = doesBounce;
	}

}
