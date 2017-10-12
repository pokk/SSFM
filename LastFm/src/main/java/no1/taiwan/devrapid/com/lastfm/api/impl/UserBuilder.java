/***************************************************************************
 *   Copyright 2005-2009 Last.fm Ltd.                                      *
 *   Portions contributed by Casey Link, Lukasz Wisniewski,                *
 *   Mike Jennings, and Michael Novak Jr.                                  *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.         *
 ***************************************************************************/
package no1.taiwan.devrapid.com.lastfm.api.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import no1.taiwan.devrapid.com.lastfm.api.ImageUrl;
import no1.taiwan.devrapid.com.lastfm.api.Track;
import no1.taiwan.devrapid.com.lastfm.api.User;
import no1.taiwan.devrapid.com.lastfm.util.XMLUtil;
import no1.taiwan.devrapid.com.lastfm.xml.XMLBuilder;
import org.w3c.dom.Node;


/**
 * @author jennings Date: Oct 20, 2008
 */
public class UserBuilder extends XMLBuilder<User> {
    private ImageUrlBuilder imageBuilder = new ImageUrlBuilder();
    private TrackBuilder trackBuilder = new TrackBuilder();

    @Override
    public User build(Node userNode) {
        node = userNode;
        String name = getText("name");
        String realname = getText("realname");
        String url = getText("url");
        String age = getText("age");
        String playcount = getText("playcount");
        String subscriber = getText("subscriber");

        List<Node> imageNodes = getChildNodes("image");
        if (imageNodes.size() > 1) {
            imageNodes.remove(0); // remove smallest size if there is one
        }
        ImageUrl[] images = new ImageUrl[imageNodes.size()];
        int i = 0;
        for (Node imageNode : imageNodes) {
            images[i++] = imageBuilder.build(imageNode);
        }

        // create locale for country
        Locale countryLocale = null;
        String country = getText("country");
        if (country != null && country.trim().length() > 0) {
            countryLocale = new Locale("", country);
        }

        // create date from UNIX time
        Date registeredDate = null;
        Node registered = getChildNode("registered");
        if (registered != null) {
            String date = XMLUtil.getNodeAttribute(registered, "unixtime");
            registeredDate = new Date(Long.parseLong(date) * 1000);
        }

        User.Gender genderEnum = User.Gender.UNKNOWN;
        String gender = getText("gender");
        if (gender != null) {
            if (gender.equalsIgnoreCase("m")) {
                genderEnum = User.Gender.MALE;
            }
            else if (gender.equalsIgnoreCase("f")) {
                genderEnum = User.Gender.FEMALE;
            }
        }

        Track recentTrack = null;
        Node trackNode = getChildNode("recenttrack");
        if (trackNode != null) {
            recentTrack = trackBuilder.build(trackNode);
        }

        return new User(name,
                        realname,
                        url,
                        images,
                        countryLocale,
                        age,
                        genderEnum,
                        playcount,
                        subscriber,
                        registeredDate,
                        recentTrack);
    }
}
