package no1.taiwan.devrapid.com.lastfm.api.impl;

import no1.taiwan.devrapid.com.lastfm.api.Metro;
import no1.taiwan.devrapid.com.lastfm.xml.XMLBuilder;
import org.w3c.dom.Node;


public class MetroBuilder extends XMLBuilder<Metro> {
    @Override
    public Metro build(Node metroNode) {
        node = metroNode;
        String name = getText("name");
        String country = getText("country");
        return new Metro(name, country);
    }
}
