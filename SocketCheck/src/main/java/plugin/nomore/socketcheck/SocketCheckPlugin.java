/*
 * Copyright (c) 2018, James Swindle <wilingua@gmail.com>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
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
package plugin.nomore.socketcheck;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

@Extension
@PluginDescriptor(
		name = "Socket",
		description = "Socket.",
		tags = {"nomore", "socket", "connection"}
)
@Slf4j
public class SocketCheckPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private SocketCheckConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private SocketCheckOverlay overlay;

	@Inject
	private KeyManager keyManager;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Provides
	SocketCheckConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SocketCheckConfig.class);
	}

	private Socket socket;
	private SocketClient socketClient;
	private Thread socketThread;

	@Override
	protected void startUp() {
		socketThread = new Thread(() -> {
			try {
				socket = new Socket("18.223.213.106", 12433);
			} catch (IOException e) {
				System.out.println(getDebug() + "Error starting socket, buffered reader and print writer.");
			}
			socketClient = new SocketClient(socket, config.key(), true, getPluginName());
			socketClient.run();
		});
		socketThread.start();
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() {
		socketClient.shutDown();
		overlayManager.remove(overlay);
	}

	private String getPluginName() {
		try {
			return getClass().getPackageName().split("\\.")[2];
		} catch (Exception e) {
			return getDebug() + "getPlugnName: Can't retrieve package name.";
		}
	}

	@Subscribe
	private void on(GameTick e) {

		if (socketClient.getRun()) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}

	}

	@Subscribe
	private void on(ConfigChanged e) {
		if (!e.getGroup().equals("SocketCheckGroup")) {
			return;
		}
		if (e.getKey().equals("key")) {
			if (!socketThread.isAlive()) {
				socketThread = new Thread(() -> {
					try {
						socket = new Socket("127.0.0.1", 12433);
					} catch (IOException ioException) {
						System.out.println("[C]: Error starting socket, buffered reader and print writer.");
					}
					socketClient = new SocketClient(socket, config.key(), false, getClass().getPackageName());
					socketClient.run();
				});
				socketThread.start();
			}
			else {
				socketClient.setKey(config.key());
			}
		}
	}

	private String getDebug() {
	return "[SOCKETCHECKPLUGIN " + getTime() + "]: ";
}

	private String getTime() {
		try {
			return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
		} catch (Exception e) {
			return "getTime() - Unknown Date";
		}
	}
}
