package no1.taiwan.devrapid.com.lastfm.api.impl;

import no1.taiwan.devrapid.com.lastfm.api.Geo;
import no1.taiwan.devrapid.com.lastfm.xml.XMLBuilder;
import org.w3c.dom.Node;


public class GeoBuilder extends XMLBuilder<Geo> {
    @Override
    public Geo build(Node geoNode) {
        node = geoNode;
        String countrycode = getText("countrycode");
        String countryname = getText("countryname");
        return new Geo(countrycode, countryname);
    }
}
