package com.ottogi.be.spot.util;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PointConverter implements AttributeConverter<Point, String> {

    private final WKTWriter writer = new WKTWriter();
    private final WKTReader reader = new WKTReader();

    @Override
    public String convertToDatabaseColumn(Point point) {
        if (point == null) {
            return null;
        }
        return writer.write(point);
    }

    @Override
    public Point convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return (Point) reader.read(dbData);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not convert String to Point", e);
        }
    }
}
