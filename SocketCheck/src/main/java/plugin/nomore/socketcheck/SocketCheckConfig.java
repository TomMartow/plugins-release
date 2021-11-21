/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigTitle;

@ConfigGroup("SocketCheckGroup")
public interface SocketCheckConfig extends Config
{

    @ConfigTitle(
            keyName = "keyTitle",
            name = "Authentication",
            description = "",
            position = 1
    )
    String keyTitle = "keyTitle";

    @ConfigItem(
            keyName = "key",
            name = "Key",
            description = "Paste the authentication key you have purchased",
            position = 2,
            title = "keyTitle"
    )
    default String key() { return ""; }

    @ConfigItem(
            keyName = "ip",
            name = "Server IP",
            description = "",
            position = 2,
            title = "keyTitle"
    )
    default String ip() { return "18.223.213.106"; }

    @ConfigItem(
            keyName = "useLocalhost",
            name = "Use localhost",
            description = "",
            position = 3,
            title = "keyTitle"
    )
    default boolean localhost() { return false; }

    @ConfigItem(
            keyName = "port",
            name = "Port",
            description = "Paste the authentication key you have purchased",
            position = 4,
            title = "keyTitle"
    )
    default String port() { return "12433"; }

}