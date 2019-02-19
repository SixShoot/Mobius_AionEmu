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
package com.aionemu.gameserver.dataholders.loadingutils;

import java.io.File;
import java.io.FileReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.aionemu.gameserver.dataholders.StaticData;

/**
 * This class is responsible for loading xml files. It uses JAXB to do the job.<br>
 * In addition, it uses @{link {@link XmlMerger} to create input file from all xml files.
 * @author Luno
 */
public class XmlDataLoader
{
	private static final Logger log = LoggerFactory.getLogger(XmlDataLoader.class);
	
	/** File containing xml schema declaration */
	private static final String XML_SCHEMA_FILE = "./data/static_data/static_data.xsd";
	
	private static final String CACHE_DIRECTORY = "./cache/";
	private static final String CACHE_XML_FILE = "./cache/static_data.xml";
	private static final String MAIN_XML_FILE = "./data/static_data/static_data.xml";
	
	public static XmlDataLoader getInstance()
	{
		return SingletonHolder.instance;
	}
	
	private XmlDataLoader()
	{
		
	}
	
	/**
	 * Creates {@link StaticData} object based on xml files, starting from static_data.xml
	 * @return StaticData object, containing all game data defined in xml files
	 */
	public StaticData loadStaticData()
	{
		makeCacheDirectory();
		
		final File cachedXml = new File(CACHE_XML_FILE);
		final File cleanMainXml = new File(MAIN_XML_FILE);
		
		mergeXmlFiles(cachedXml, cleanMainXml);
		
		try
		{
			final JAXBContext jc = JAXBContext.newInstance(StaticData.class);
			final Unmarshaller un = jc.createUnmarshaller();
			un.setEventHandler(new XmlValidationHandler());
			un.setSchema(getSchema());
			return (StaticData) un.unmarshal(new FileReader(CACHE_XML_FILE));
		}
		/*
		 * catch (IllegalAnnotationsException e) { log.error("Error while loading static data", e); throw new Error("Error while loading static data", e); } catch (FileNotFoundException e) { log.error("Error while loading static data", e); throw new Error("Error while loading static data", e); }
		 * catch (JAXBException e) { log.error("Error while loading static data", e); throw new Error("Error while loading static data", e); }
		 */
		catch (Exception e)
		{
			log.error("Error while loading static data", e);
		}
		return null;
	}
	
	/**
	 * Creates and returns {@link Schema} object representing xml schema of xml files
	 * @return a Schema object.
	 */
	private Schema getSchema()
	{
		Schema schema = null;
		final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		try
		{
			schema = sf.newSchema(new File(XML_SCHEMA_FILE));
		}
		catch (SAXException saxe)
		{
			log.error("Error while getting schema", saxe);
			throw new Error("Error while getting schema", saxe);
		}
		
		return schema;
	}
	
	/** Creates directory for cache files if it doesn't already exist */
	private void makeCacheDirectory()
	{
		final File cacheDir = new File(CACHE_DIRECTORY);
		if (!cacheDir.exists())
		{
			cacheDir.mkdir();
		}
	}
	
	/**
	 * Merges xml files(if are newer than cache file) and puts output to cache file.
	 * @see XmlMerger
	 * @param cachedXml
	 * @param cleanMainXml
	 * @throws Error is thrown if some problem occured.
	 */
	private void mergeXmlFiles(File cachedXml, File cleanMainXml) throws Error
	{
		final XmlMerger merger = new XmlMerger(cleanMainXml, cachedXml);
		try
		{
			merger.process();
		}
		catch (Exception e)
		{
			log.error("Error while merging xml files", e);
			throw new Error("Error while merging xml files", e);
		}
	}
	
	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		
		protected static final XmlDataLoader instance = new XmlDataLoader();
	}
}
