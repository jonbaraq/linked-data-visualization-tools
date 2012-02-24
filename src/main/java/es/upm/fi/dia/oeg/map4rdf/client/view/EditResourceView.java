/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.fi.dia.oeg.map4rdf.client.view;

import name.alexdeleon.lib.gwtblocks.client.widget.loading.LoadingWidget;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.docs.Toolbar;

import es.upm.fi.dia.oeg.map4rdf.client.presenter.EditResourcePresenter;
import es.upm.fi.dia.oeg.map4rdf.client.resource.BrowserResources;
import es.upm.fi.dia.oeg.map4rdf.client.widget.DescriptionTreeItem;
import es.upm.fi.dia.oeg.map4rdf.client.widget.WidgetFactory;

/**
 *
 * @author filip
 */
public class EditResourceView extends Composite implements EditResourcePresenter.Display {
    
    private ScrollPanel mainPanel;
    private HorizontalPanel container;
    
    private Tree tree;
    private TreeItem root;
    //@TODO refactor as a db parameter
    private Integer maxDepth = 3; 
    private final LoadingWidget loadingWidget;
    private BrowserResources resources;
    private PushButton saveButton;
    private PushButton backButton;
    
    @Inject
    public EditResourceView(WidgetFactory widgetFactory, BrowserResources resources) {
        loadingWidget = widgetFactory.getLoadingWidget();
        this.resources = resources;
        initWidget(createUi());
    }
    
    @Override
    public void clear() {
    	container.remove(tree);
    	tree = new Tree();
    	root = new TreeItem("");
    	root.addStyleName(resources.css().treeRoot());
    	tree.addItem(root);
    	container.add(tree);
    }
    
    @Override
    public Widget asWidget() {
        return this;
    }
    
    @Override
    public void startProcessing() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void stopProcessing() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private Widget createUi() {
    	mainPanel = new ScrollPanel();    	
    	tree = new Tree();
    	root = new TreeItem("");
    	backButton = new PushButton(new Image(resources.backButton()));
    	saveButton = new PushButton(new Image(resources.saveButton()));
    	backButton.setSize("25px", "25px");
    	saveButton.setSize("25px", "25px");
    	tree.addItem(root);
    	root.setStyleName("treeRoot");
    	container = new HorizontalPanel();
    	VerticalPanel toolbar = new VerticalPanel();
    	
    	toolbar.setSpacing(2);
    	
    	container.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
    	
    	toolbar.add(saveButton);
    	toolbar.add(backButton);

    	container.add(tree);
    	container.add(toolbar);
    	
    	mainPanel.add(container);
        return mainPanel;
    }

	@Override
	public void setCore(String core) {
		root.setText(core);
	}

	@Override
	public void addDescription(DescriptionTreeItem description) {
		TreeItem treeItem = new TreeItem(description.getWidget());
		if (description.getSubjectDescriptions().getObject().isResource() && description.getDepth() < maxDepth) {
			treeItem.addItem("");
		}
		root.addItem(treeItem);
	}

	@Override
	public Tree getTree() {
		return tree;
	}

	@Override
	public void addDescription(TreeItem treeItem, DescriptionTreeItem description ) {
		TreeItem leaf = new TreeItem(description.getWidget());
		if (description.getSubjectDescriptions().getObject().isResource() && description.getDepth() < maxDepth) {
			leaf.addItem("");
		}
		treeItem.addItem(leaf);
	}

	@Override
	public void openLoadWidget() {
		loadingWidget.center();
	}

	@Override
	public void closeLoadWidget() {
		loadingWidget.hide();
	}
}
