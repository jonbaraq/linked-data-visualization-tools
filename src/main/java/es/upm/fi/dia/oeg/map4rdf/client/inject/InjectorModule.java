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
package es.upm.fi.dia.oeg.map4rdf.client.inject;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;
import es.upm.fi.dia.oeg.map4rdf.client.maplet.Maplet;
import es.upm.fi.dia.oeg.map4rdf.client.maplet.stats.StatisticsMaplet;
import es.upm.fi.dia.oeg.map4rdf.client.maplet.stats.StatisticsPresenter;
import es.upm.fi.dia.oeg.map4rdf.client.maplet.stats.StatisticsView;
import es.upm.fi.dia.oeg.map4rdf.client.presenter.DashboardPresenter;
import es.upm.fi.dia.oeg.map4rdf.client.presenter.EditResourcePresenter;
import es.upm.fi.dia.oeg.map4rdf.client.presenter.FacetPresenter;
import es.upm.fi.dia.oeg.map4rdf.client.presenter.FiltersPresenter;
import es.upm.fi.dia.oeg.map4rdf.client.presenter.MapOverlaysPresenter;
import es.upm.fi.dia.oeg.map4rdf.client.presenter.MapPresenter;
import es.upm.fi.dia.oeg.map4rdf.client.presenter.ResultsPresenter;
import es.upm.fi.dia.oeg.map4rdf.client.presenter.ShapeFilesPresenter;
import es.upm.fi.dia.oeg.map4rdf.client.presenter.ShapeFilesPresenter.Display;
import es.upm.fi.dia.oeg.map4rdf.client.view.DashboardView;
import es.upm.fi.dia.oeg.map4rdf.client.view.EditResourceView;
import es.upm.fi.dia.oeg.map4rdf.client.view.FacetView;
import es.upm.fi.dia.oeg.map4rdf.client.view.FiltersView;
import es.upm.fi.dia.oeg.map4rdf.client.view.MapOverlaysView;
import es.upm.fi.dia.oeg.map4rdf.client.view.OpenLayersMapView;
import es.upm.fi.dia.oeg.map4rdf.client.view.ResultsView;
import es.upm.fi.dia.oeg.map4rdf.client.view.ShapeFilesView;

/**
 * @author Alexander De Leon
 */
public class InjectorModule extends AbstractPresenterModule {

	@Override
	protected void configure() {

		// Events
		bind(EventBus.class).to(DefaultEventBus.class);

		// MVP
		bindDisplay(DashboardPresenter.Display.class, DashboardView.class);
		bindDisplay(EditResourcePresenter.Display.class, EditResourceView.class);
                bindDisplay(FacetPresenter.Display.class, FacetView.class);
                bindDisplay(FiltersPresenter.Display.class, FiltersView.class);
                bindDisplay(MapPresenter.Display.class, OpenLayersMapView.class);
		bindDisplay(ResultsPresenter.Display.class, ResultsView.class);
                bindDisplay(ShapeFilesPresenter.Display.class, ShapeFilesView.class);
		bindDisplay(MapOverlaysPresenter.Display.class, MapOverlaysView.class);
		bindDisplay(StatisticsPresenter.Display.class, StatisticsView.class);

		// Maplets
		bind(Maplet.class).to(StatisticsMaplet.class).asEagerSingleton();
	}

}
