/*
 * AsyncWorldEdit a performance improvement plugin for Minecraft WorldEdit plugin.
 * Copyright (c) 2018, SBPrime <https://github.com/SBPrime/>
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
package org.primesoft.asyncworldedit.worldedit.blocks;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.registry.state.Property;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockType;
import java.util.Map;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerEntry;
import org.primesoft.asyncworldedit.worldedit.IAsyncWrapper;

/**
 *
 * @author SBPrime
 */
public class BlockStateHolderWrapper implements BlockStateHolder, IAsyncWrapper {
    public static BlockStateHolderWrapper wrap(BlockStateHolder block, int jobId,
                                        boolean isAsync, IPlayerEntry player) {
        BlockStateHolderWrapper result;
        if (block instanceof BlockStateHolderWrapper) {
            result = (BlockStateHolderWrapper) block;
            result.setAsync(isAsync);
            result.setPlayer(player);
        } else {
            result = new BlockStateHolderWrapper(block, jobId, isAsync, player);
        }

        return result;
    }
    
    private final BlockStateHolder m_parent;

    private final int m_jobId;

    private boolean m_isAsync;

    private IPlayerEntry m_player;
    
    @Override
    public int getJobId() {
        return m_jobId;
    }

    @Override
    public BlockStateHolder getParent() {
        return m_parent;
    }

    @Override
    public boolean isAsync() {
        return m_isAsync;
    }

    public void setAsync(boolean async) {
        m_isAsync = async;
    }

    public void setPlayer(IPlayerEntry player) {
        m_player = player;
    }

    @Override
    public IPlayerEntry getPlayer() {
        return m_player;
    }
    
    private BlockStateHolderWrapper(BlockStateHolder parent, int jobId, boolean isAsync,
                             IPlayerEntry player) {
        m_jobId = jobId;
        m_parent = parent;
        m_isAsync = isAsync;
        m_player = player;
    }

    @Override
    public BlockType getBlockType() {
        return m_parent.getBlockType();
    }

    @Override
    public BlockStateHolder with(Property property, Object value) {
        return m_parent.with(property, value);
    }

    @Override
    public Object getState(Property property) {
        return m_parent.getState(property);
    }

    @Override
    public Map getStates() {
        return m_parent.getStates();
    }

    @Override
    public boolean equalsFuzzy(BlockStateHolder o) {
        return m_parent.equalsFuzzy(o);
    }

    @Override
    public BlockState toImmutableState() {
        return m_parent.toImmutableState();
    }

    @Override
    public BaseBlock toBaseBlock() {
        return m_parent.toBaseBlock();
    }

    @Override
    public BaseBlock toBaseBlock(CompoundTag compoundTag) {
        return m_parent.toBaseBlock(compoundTag);
    }

    @Override
    public String getAsString() {
        return m_parent.getAsString();
    }
}
