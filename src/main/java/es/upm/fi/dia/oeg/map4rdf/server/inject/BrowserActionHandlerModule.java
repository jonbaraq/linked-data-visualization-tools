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
package es.upm.fi.dia.oeg.map4rdf.server.inject;

import es.upm.fi.dia.oeg.map4rdf.server.command.*;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
/**
 * @author Alexander De Leon
 */
public class BrowserActionHandlerModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(GetGeoResourcesHandler.class);
                bindHandler(GetGeoResourcesFromRdfModelHandler.class);
		bindHandler(GetFacetDefinitionsHandler.class);
                bindHandler(GetGeoResourceOverlaysHandler.class);
		bindHandler(GetStatisticYearsHandler.class);
		bindHandler(GetStatisticDatasetsHandler.class);
		bindHandler(GetGeoResourceHandler.class);
		bindHandler(GetSubjectDescriptionsHandler.class);
		bindHandler(GetSubjectLabelHandler.class);
		bindHandler(GetGeoResourcesAsKmlUrlHandler.class);
		bindHandler(SaveRdfFileHandler.class);
		bindHandler(GetConfigurationParameterHandler.class);
	}
}
