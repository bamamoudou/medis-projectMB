package com.mediscreen.msclientui;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class JWTTest {
	public static String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTgwODA1NTgsInVzZXJJRCI6IjA5MzE4NDlhLThkNjgtNGQ2Yy1hOTNlLTg0ODc3ZDk1MzE5MSIsInVzZXJuYW1lIjoicGFzc3dvcmQifQ.CZPmiEOPHVujJsIO8e-OMa6jRq_BLUgjIBRgkNRrnCk";
	public static String wrongToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTgwODA1NTgsInVzZXJJRCI6IjA5MzE4NDlhLThkNjgtNGQ2Yy1hOTNl";

	public static HttpSession session = new HttpSession() {
		@Override
		public long getCreationTime() {
			return 0;
		}

		@Override
		public String getId() {
			return null;
		}

		@Override
		public long getLastAccessedTime() {
			return 0;
		}

		@Override
		public ServletContext getServletContext() {
			return null;
		}

		@Override
		public void setMaxInactiveInterval(int i) {

		}

		@Override
		public int getMaxInactiveInterval() {
			return 0;
		}

		@Override
		public HttpSessionContext getSessionContext() {
			return null;
		}

		@Override
		public Object getAttribute(String s) {
			return null;
		}

		@Override
		public Object getValue(String s) {
			return null;
		}

		@Override
		public Enumeration<String> getAttributeNames() {
			return null;
		}

		@Override
		public String[] getValueNames() {
			return new String[0];
		}

		@Override
		public void setAttribute(String s, Object o) {

		}

		@Override
		public void putValue(String s, Object o) {

		}

		@Override
		public void removeAttribute(String s) {

		}

		@Override
		public void removeValue(String s) {

		}

		@Override
		public void invalidate() {

		}

		@Override
		public boolean isNew() {
			return false;
		}
	};
}