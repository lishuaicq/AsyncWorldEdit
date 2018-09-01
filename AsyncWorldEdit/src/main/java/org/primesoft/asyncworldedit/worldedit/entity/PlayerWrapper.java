/*
 * AsyncWorldEdit a performance improvement plugin for Minecraft WorldEdit plugin.
 * Copyright (c) 2014, SBPrime <https://github.com/SBPrime/>
 * Copyright (c) AsyncWorldEdit contributors
 *
 * All rights reserved.
 *
 * Redistribution in source, use in source and binary forms, with or without
 * modification, are permitted free of charge provided that the following 
 * conditions are met:
 *
 * 1.  Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 * 2.  Redistributions of source code, with or without modification, in any form
 *     other then free of charge is not allowed,
 * 3.  Redistributions of source code, with tools and/or scripts used to build the 
 *     software is not allowed,
 * 4.  Redistributions of source code, with information on how to compile the software
 *     is not allowed,
 * 5.  Providing information of any sort (excluding information from the software page)
 *     on how to compile the software is not allowed,
 * 6.  You are allowed to build the software for your personal use,
 * 7.  You are allowed to build the software using a non public build server,
 * 8.  Redistributions in binary form in not allowed.
 * 9.  The original author is allowed to redistrubute the software in bnary form.
 * 10. Any derived work based on or containing parts of this software must reproduce
 *     the above copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided with the
 *     derived work.
 * 11. The original author of the software is allowed to change the license
 *     terms or the entire license of the software as he sees fit.
 * 12. The original author of the software is allowed to sublicense the software
 *     or its parts using any license terms he sees fit.
 * 13. By contributing to this project you agree that your contribution falls under this
 *     license.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.primesoft.asyncworldedit.worldedit.entity;

import com.sk89q.worldedit.PlayerDirection;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.blocks.BaseItemStack;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.inventory.BlockBag;
import com.sk89q.worldedit.internal.cui.CUIEvent;
import com.sk89q.worldedit.session.SessionKey;
import com.sk89q.worldedit.util.HandSide;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.util.auth.AuthorizationException;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.gamemode.GameMode;
import java.io.File;
import java.util.UUID;
import org.primesoft.asyncworldedit.core.AwePlatform;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerEntry;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerManager;
import org.primesoft.asyncworldedit.utils.Pair;
import org.primesoft.asyncworldedit.worldedit.world.AsyncWorld;

/**
 *
 * @author SBPrime
 */
public class PlayerWrapper implements Player {

    /**
     * The parrent class
     */
    private final Player m_parent;

    private final Object m_mutex = new Object();

    private UUID m_uuid = null;

    private IPlayerEntry m_entry = null;

    private Pair<World, AsyncWorld> m_world = null;
        
    public PlayerWrapper(Player player) {
        m_parent = player;
    }

    private IPlayerEntry getEntry() {
        if (m_entry == null) {
            synchronized (m_mutex) {
                if (m_entry == null) {
                    IPlayerManager pm = AwePlatform.getInstance().getCore().getPlayerManager();
                    m_entry = pm.getPlayer(m_parent.getName());
                }
            }
        }
        return m_entry;
    }

    /**
     * Ge the player UUID
     *
     * @return
     */
    public UUID getUUID() {
        if (m_uuid == null) {
            synchronized (m_mutex) {
                if (m_uuid == null) {
                    if (m_parent instanceof BukkitPlayer) {
                        m_uuid = ((BukkitPlayer) m_parent).getPlayer().getUniqueId();
                    } else {
                        IPlayerManager pm = AwePlatform.getInstance().getCore().getPlayerManager();
                        IPlayerEntry entry = pm.getPlayer(m_parent.getName());
                        return entry.getUUID();
                    }
                }
            }
        }
        return m_uuid;
    }

    @Override
    public boolean ascendLevel() {
        return m_parent.ascendLevel();
    }

    @Override
    public boolean ascendToCeiling(int clearance) {
        return m_parent.ascendToCeiling(clearance);
    }

    @Override
    public boolean ascendToCeiling(int clearance, boolean alwaysGlass) {
        return m_parent.ascendToCeiling(clearance, alwaysGlass);
    }

    @Override
    public boolean ascendUpwards(int distance) {
        return m_parent.ascendUpwards(distance);
    }

    @Override
    public boolean ascendUpwards(int distance, boolean alwaysGlass) {
        return m_parent.ascendUpwards(distance, alwaysGlass);
    }

    @Override
    public boolean canDestroyBedrock() {
        return m_parent.canDestroyBedrock();
    }

    @Override
    public void checkPermission(String string) throws AuthorizationException {
        m_parent.checkPermission(string);
    }

    @Override
    public boolean descendLevel() {
        return m_parent.descendLevel();
    }

    @Override
    public void dispatchCUIEvent(CUIEvent event) {
        m_parent.dispatchCUIEvent(event);
    }

    @Override
    public void findFreePosition() {
        m_parent.findFreePosition();
    }

    @Override
    public void findFreePosition(Location searchPos) {
        m_parent.findFreePosition(searchPos);
    }

    @Override
    public void floatAt(int x, int y, int z, boolean alwaysGlass) {
        m_parent.floatAt(x, y, z, alwaysGlass);
    }

    @Override
    public Location getBlockIn() {
        return m_parent.getBlockIn();
    }

    @Override
    public Location getBlockOn() {
        return m_parent.getBlockOn();
    }

    @Override
    public Location getBlockTrace(int range) {
        return m_parent.getBlockTrace(range);
    }

    @Override
    public Location getBlockTrace(int range, boolean useLastBlock) {
        return m_parent.getBlockTrace(range, useLastBlock);
    }

    @Override
    public Location getBlockTraceFace(int range, boolean useLastBlock) {
        return m_parent.getBlockTraceFace(range, useLastBlock);
    }

    @Override
    public PlayerDirection getCardinalDirection() {
        return m_parent.getCardinalDirection();
    }

    @Override
    public PlayerDirection getCardinalDirection(int yawOffset) {
        return m_parent.getCardinalDirection(yawOffset);
    }

    @Override
    public String[] getGroups() {
        return m_parent.getGroups();
    }

    @Override
    public BaseItemStack getItemInHand(HandSide hand) {
        return m_parent.getItemInHand(hand);
    }

    @Override
    public String getName() {
        return m_parent.getName();
    }
    
    @Override
    public Location getSolidBlockTrace(int range) {
        return m_parent.getSolidBlockTrace(range);
    }

    @Override
    public World getWorld() {
        World world = m_parent.getWorld();

        synchronized (m_mutex) {
            if (m_world == null || m_world.getX1() != world) {
                AsyncWorld aWorld = AsyncWorld.wrap(world, getEntry());
                if (aWorld != null) {
                    m_world = new Pair<>(world, aWorld);
                    world = aWorld;
                } else if (m_world != null) {
                    m_world = null;
                }
            } else if (m_world != null) {
                world = m_world.getX2();
            }
        }

        return world;
    }

    @Override
    public void giveItem(BaseItemStack itemStack) {
        m_parent.giveItem(itemStack);
    }

    @Override
    public GameMode getGameMode() {
        return m_parent.getGameMode();
    }

    @Override
    public boolean hasPermission(String perm) {
        return m_parent.hasPermission(perm);
    }

    @Override
    public int hashCode() {
        return m_parent.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PlayerWrapper) {
            o = ((PlayerWrapper) o).m_parent;
        }
        return m_parent.equals(o);
    }

    @Override
    public boolean isHoldingPickAxe() {
        return m_parent.isHoldingPickAxe();
    }

    @Override
    public boolean isPlayer() {
        return m_parent.isPlayer();
    }

    @Override
    public File openFileOpenDialog(String[] extensions) {
        return m_parent.openFileOpenDialog(extensions);
    }

    @Override
    public File openFileSaveDialog(String[] extensions) {
        return m_parent.openFileSaveDialog(extensions);
    }

    @Override
    public boolean passThroughForwardWall(int range) {
        return m_parent.passThroughForwardWall(range);
    }

    @Override
    public void printDebug(String msg) {
        m_parent.printDebug(msg);
    }

    @Override
    public void printError(String msg) {
        m_parent.printError(msg);
    }

    @Override
    public void print(String msg) {
        m_parent.print(msg);
    }

    @Override
    public void printRaw(String msg) {
        m_parent.printRaw(msg);
    }

    @Override
    public void setOnGround(Location searchPos) {
        m_parent.setOnGround(searchPos);
    }

    @Override
    public void setPosition(Vector pos) {
        m_parent.setPosition(pos);
    }

    @Override
    public void setPosition(Vector pos, float pitch, float yaw) {
        m_parent.setPosition(pos, pitch, yaw);
    }

    @Override
    public BaseBlock getBlockInHand(HandSide hand)
            throws WorldEditException {
        return m_parent.getBlockInHand(hand);
    }

    @Override
    public BlockBag getInventoryBlockBag() {
        return m_parent.getInventoryBlockBag();
    }

    @Override
    public String toString() {
        return m_parent.toString();
    }

    @Override
    public Location getLocation() {
        return m_parent.getLocation();
    }

    @Override
    public BaseEntity getState() {
        return m_parent.getState();
    }

    @Override
    public Extent getExtent() {
        return m_parent.getExtent();
    }

    @Override
    public boolean remove() {
        return m_parent.remove();
    }

    @Override
    public <T> T getFacet(Class<? extends T> type) {
        return m_parent.getFacet(type);
    }

    @Override
    public UUID getUniqueId() {
        return m_parent.getUniqueId();
    }

    @Override
    public SessionKey getSessionKey() {
        return m_parent.getSessionKey();
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        m_parent.setGameMode(gameMode);
    }

    @Override
    public void sendFakeBlock(Vector pos, BlockStateHolder block) {
        m_parent.sendFakeBlock(pos, block);
    }
}
