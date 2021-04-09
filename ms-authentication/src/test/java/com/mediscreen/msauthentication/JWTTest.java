package com.mediscreen.msauthentication;

import io.jsonwebtoken.Claims;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class JWTTest {
    public static String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTgwODA1NTgsInVzZXJJRCI6IjA5MzE4NDlhLThkNjgtNGQ2Yy1hOTNlLTg0ODc3ZDk1MzE5MSIsInVzZXJuYW1lIjoicGFzc3dvcmQifQ.CZPmiEOPHVujJsIO8e-OMa6jRq_BLUgjIBRgkNRrnCk";
    public static String wrongToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTgwODA1NTgsInVzZXJJRCI6IjA5MzE4NDlhLThkNjgtNGQ2Yy1hOTNl";

    public static Claims claims = new Claims() {
        @Override
        public String getIssuer() {
            return null;
        }

        @Override
        public Claims setIssuer(String s) {
            return null;
        }

        @Override
        public String getSubject() {
            return null;
        }

        @Override
        public Claims setSubject(String s) {
            return null;
        }

        @Override
        public String getAudience() {
            return null;
        }

        @Override
        public Claims setAudience(String s) {
            return null;
        }

        @Override
        public Date getExpiration() {
            return null;
        }

        @Override
        public Claims setExpiration(Date date) {
            return null;
        }

        @Override
        public Date getNotBefore() {
            return null;
        }

        @Override
        public Claims setNotBefore(Date date) {
            return null;
        }

        @Override
        public Date getIssuedAt() {
            return null;
        }

        @Override
        public Claims setIssuedAt(Date date) {
            return null;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public Claims setId(String s) {
            return null;
        }

        @Override
        public <T> T get(String s, Class<T> aClass) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public Object get(Object key) {
            return null;
        }

        @Override
        public Object put(String key, Object value) {
            return null;
        }

        @Override
        public Object remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends String, ?> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<String> keySet() {
            return null;
        }

        @Override
        public Collection<Object> values() {
            return null;
        }

        @Override
        public Set<Entry<String, Object>> entrySet() {
            return null;
        }
    };
}
