/*
 * This file is part of the Aion-Emu project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.chatserver.network.gs.clientpackets;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.chatserver.network.gs.GsAuthResponse;
import com.aionemu.chatserver.network.gs.GsClientPacket;
import com.aionemu.chatserver.network.gs.GsConnection;
import com.aionemu.chatserver.network.gs.GsConnection.State;
import com.aionemu.chatserver.network.gs.serverpackets.SM_GS_AUTH_RESPONSE;
import com.aionemu.chatserver.service.GameServerService;

/**
 * @author ATracer
 */
public class CM_CS_AUTH extends GsClientPacket
{
	
	private final Logger log = LoggerFactory.getLogger(CM_CS_AUTH.class);
	/**
	 * Password for authentication
	 */
	private String password;
	/**
	 * Id of GameServer
	 */
	private byte gameServerId;
	/**
	 * Default address for server
	 */
	private byte[] defaultAddress;
	
	public CM_CS_AUTH(ByteBuffer buf, GsConnection connection)
	{
		super(buf, connection, 0x00);
	}
	
	@Override
	protected void readImpl()
	{
		gameServerId = (byte) readC();
		defaultAddress = readB(readC());
		password = readS();
	}
	
	@Override
	protected void runImpl()
	{
		final GsAuthResponse resp = GameServerService.getInstance().registerGameServer(gameServerId, defaultAddress, password);
		
		switch (resp)
		{
			case AUTHED:
				getConnection().setState(State.AUTHED);
				getConnection().sendPacket(new SM_GS_AUTH_RESPONSE(resp));
				log.info("Gameserver #" + gameServerId + " is now online.");
				break;
			case NOT_AUTHED:
				getConnection().sendPacket(new SM_GS_AUTH_RESPONSE(resp));
				break;
			case ALREADY_REGISTERED:
				log.info("Gameserver #" + gameServerId + " is already registered!");
				getConnection().sendPacket(new SM_GS_AUTH_RESPONSE(resp));
				break;
			// default:
			// getConnection().close(new SM_GS_AUTH_RESPONSE(resp), false);
		}
	}
}