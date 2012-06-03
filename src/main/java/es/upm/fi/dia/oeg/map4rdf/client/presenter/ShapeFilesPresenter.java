/**
 * Copyright (c) 2012 Ontology Engineering Group, Departamento de Inteligencia
 * Artificial, Facultad de Informetica, Universidad Politecnica de Madrid, Spain
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
package es.upm.fi.dia.oeg.map4rdf.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.upm.fi.dia.oeg.map4rdf.client.event.ShapeFilesChangedEvent;
import name.alexdeleon.lib.gwtblocks.client.ControlPresenter;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

/**
 * @author Jonathan Gonzalez
 */
@Singleton
public final class ShapeFilesPresenter
        extends ControlPresenter<ShapeFilesPresenter.Display> {

    public interface Display extends WidgetDisplay {

        public void clear();

        public Button getSubmitUploadButton();
        
        public Button getSubmitUrlButton();

        public FormPanel getFormUpload();
    }
    private final DispatchAsync dispatchAsync;
    private MapPresenter mapPresenter;

    @Inject
    public ShapeFilesPresenter(
            ShapeFilesPresenter.Display display, EventBus eventBus, DispatchAsync dispatchAsync,
            MapPresenter mapPresenter) {
        super(display, eventBus);
        this.dispatchAsync = dispatchAsync;
        this.mapPresenter = mapPresenter;
        onBind();
    }

    /*
     * -------------- Presenter callbacks --
     */
    @Override
    protected void onBind() {
        final FormPanel form = getDisplay().getFormUpload();
        
        getDisplay().getSubmitUrlButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                form.submit();
            }
        });
        
        getDisplay().getSubmitUploadButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                form.submit();
            }
        });

        form.addSubmitHandler(new FormPanel.SubmitHandler() {

            @Override
            public void onSubmit(SubmitEvent event) {
                // Window.alert("Submitting!");
            }
        });

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

            @Override
            public void onSubmitComplete(SubmitCompleteEvent event) {
                String message = event.getResults().split(">")[1].split("<")[0];
                String fileName = "";
                if (message.contains("successfully")) {
                    fileName = message.split(": ")[1];
                    eventBus.fireEvent(new ShapeFilesChangedEvent(fileName));
                }
            }
        });
    }

    public void clear() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onUnbind() {
        // TODO Auto-generated method stub
    }

    @Override
    public void refreshDisplay() {
        // TODO Auto-generated method stub
    }

    @Override
    public void revealDisplay() {
        // TODO Auto-generated method stub
    }
    
}