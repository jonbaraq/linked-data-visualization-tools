/**
 * Copyright (c) 2012 Ontology Engineering Group, Departamento de Inteligencia
 * Artificial, Facultad de Informatica, Universidad Politecnica de Madrid, Spain
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package es.upm.fi.dia.oeg.map4rdf.client.action;

import net.customware.gwt.dispatch.shared.Action;
import es.upm.fi.dia.oeg.map4rdf.share.BoundingBox;
import es.upm.fi.dia.oeg.map4rdf.share.GeoResource;

/**
 * @author Jonathan Gonzalez (jonathan@jonbaraq.eu)
 */
public class GetGeoResourcesFromRdfModel
        extends GetGeoResourcesFromRdfModelBase
        implements Action<ListResult<GeoResource>> {

    GetGeoResourcesFromRdfModel() {
        // for serialization
        super();
    }

    public GetGeoResourcesFromRdfModel(BoundingBox boundingBox) {
        super(boundingBox);
    }
    
}