/**
 * Copyright (c) 2011 Ontology Engineering Group, 
 * Departamento de Inteligencia Artificial,
 * Facultad de Informetica, Universidad 
 * Politecnica de Madrid, Spain
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package es.upm.fi.dia.oeg.map4rdf.server.dao;

import java.util.List;
import java.util.Set;

import es.upm.fi.dia.oeg.map4rdf.share.BoundingBox;
import es.upm.fi.dia.oeg.map4rdf.share.FacetConstraint;
import es.upm.fi.dia.oeg.map4rdf.share.Facet;
import es.upm.fi.dia.oeg.map4rdf.share.GeoResource;
import es.upm.fi.dia.oeg.map4rdf.share.GeoResourceOverlay;
import es.upm.fi.dia.oeg.map4rdf.share.Resource;
import es.upm.fi.dia.oeg.map4rdf.share.SubjectDescription;
import es.upm.fi.dia.oeg.map4rdf.share.StatisticDefinition;
import es.upm.fi.dia.oeg.map4rdf.share.Year;

/**
 * @author Alexander De Leon
 */
public interface Map4rdfDao {

	List<GeoResource> getGeoResources(BoundingBox boundingBox)
			throws DaoException;

	GeoResource getGeoResource(String uri) throws DaoException;

	List<GeoResource> getGeoResources(BoundingBox boundingBox,
			Set<FacetConstraint> constraints) throws DaoException;

	List<GeoResource> getGeoResources(BoundingBox boundingBox,
			Set<FacetConstraint> constraints, int max) throws DaoException;
        
        List<GeoResource> getGeoResources(String modelConfiguration) throws DaoException;

	List<GeoResourceOverlay> getGeoResourceOverlays(
			StatisticDefinition statisticDefinition, BoundingBox boundingBox,
			Set<FacetConstraint> constraints) throws DaoException;

	List<Facet> getFacets(String predicateUri, BoundingBox boundingBox)
			throws DaoException;

	List<Year> getYears(String datasetUri) throws DaoException;

	List<Resource> getStatisticDatasets() throws DaoException;
	
	List<SubjectDescription> getSubjectDescription(String subject) throws DaoException;
	
	String getLabel(String uri) throws DaoException;
}
