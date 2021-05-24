/*
 * Copyright (C) 2011 Jason von Nieda <jason@vonnieda.org>
 * 
 * This file is part of OpenPnP.
 * 
 * OpenPnP is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * OpenPnP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with OpenPnP. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * For more information about OpenPnP visit http://openpnp.org
 */

package org.openpnp.machine.reference.camera.wizards;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.openpnp.gui.components.ComponentDecorators;
import org.openpnp.gui.support.AbstractConfigurationWizard;
import org.openpnp.gui.support.DoubleConverter;
import org.openpnp.gui.support.IntegerConverter;
import org.openpnp.gui.support.LengthConverter;
import org.openpnp.gui.support.MutableLocationProxy;
import org.openpnp.machine.reference.camera.ImageCamera;
import org.openpnp.model.Configuration;
import org.openpnp.util.UiUtils;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class ImageCameraConfigurationWizard extends AbstractConfigurationWizard {
    private final ImageCamera camera;

    private JPanel panelGeneral;
    private JLabel lblSourceUrl;
    private JTextField textFieldSourceUrl;
    private JButton btnBrowse;

    private JLabel lblCameraFlipped;

    private JCheckBox simulatedFlipped;

    private JLabel lblRotation;

    private JTextField simulatedRotation;

    public ImageCameraConfigurationWizard(ImageCamera camera) {
        this.camera = camera;

        panelGeneral = new JPanel();
        contentPanel.add(panelGeneral);
        panelGeneral.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
                "General", TitledBorder.LEADING, TitledBorder.TOP, null));
        panelGeneral.setLayout(new FormLayout(new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(70dlu;default)"),
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,},
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        lblWidth = new JLabel("Width");
        panelGeneral.add(lblWidth, "2, 2, right, default");

        width = new JTextField();
        panelGeneral.add(width, "4, 2, fill, default");
        width.setColumns(10);
        
        label_1 = new JLabel(" ");
        panelGeneral.add(label_1, "8, 2");

        lblHeight = new JLabel("Height");
        panelGeneral.add(lblHeight, "2, 4, right, default");

        height = new JTextField();
        panelGeneral.add(height, "4, 4, fill, default");
        height.setColumns(10);

        lblRotation = new JLabel("Rotation");
        panelGeneral.add(lblRotation, "2, 6, right, default");

        simulatedRotation = new JTextField();
        panelGeneral.add(simulatedRotation, "4, 6, fill, default");
        simulatedRotation.setColumns(10);
        
        lblScale = new JLabel("Viewing Scale");
        panelGeneral.add(lblScale, "2, 8, right, default");
        
        simulatedScale = new JTextField();
        panelGeneral.add(simulatedScale, "4, 8, fill, default");
        simulatedScale.setColumns(10);


        lblCameraFlipped = new JLabel("View mirrored?");
        lblCameraFlipped.setToolTipText("Simulate the camera as showing a mirrored view");
        panelGeneral.add(lblCameraFlipped, "2, 10, right, default");

        simulatedFlipped = new JCheckBox("");
        panelGeneral.add(simulatedFlipped, "4, 10");

        label = new JLabel(" ");
        panelGeneral.add(label, "6, 10");

        lblSourceUrl = new JLabel("Source URL");
        panelGeneral.add(lblSourceUrl, "2, 14, right, default");

        textFieldSourceUrl = new JTextField();
        panelGeneral.add(textFieldSourceUrl, "4, 14, 5, 1, fill, default");
        textFieldSourceUrl.setColumns(40);

        btnBrowse = new JButton(browseAction);
        panelGeneral.add(btnBrowse, "10, 14");
        
        lblX = new JLabel("X");
        panelGeneral.add(lblX, "4, 16, center, default");
        
        lblY = new JLabel("Y");
        panelGeneral.add(lblY, "6, 16, center, default");
        
        lblUnitsPerPixel = new JLabel("Units per Pixel");
        lblUnitsPerPixel.setToolTipText("To allow simulation of Unit per Pixel calibration, the true Units per Pixel of the image must be stored independently.");
        panelGeneral.add(lblUnitsPerPixel, "2, 18, right, default");
        
        imageUnitsPerPixelX = new JTextField();
        panelGeneral.add(imageUnitsPerPixelX, "4, 18, fill, default");
        imageUnitsPerPixelX.setColumns(10);
        
        imageUnitsPerPixelY = new JTextField();
        panelGeneral.add(imageUnitsPerPixelY, "6, 18, fill, default");
        imageUnitsPerPixelY.setColumns(10);
    }

    @Override
    public void createBindings() {
        DoubleConverter doubleConverter =
                new DoubleConverter(Configuration.get().getLengthDisplayFormat());
        IntegerConverter intConverter = new IntegerConverter();
        LengthConverter uppConverter = new LengthConverter("%.6f");

        addWrappedBinding(camera, "viewWidth", width, "text", intConverter);
        addWrappedBinding(camera, "viewHeight", height, "text", intConverter);

        addWrappedBinding(camera, "simulatedRotation", simulatedRotation, "text", doubleConverter);
        addWrappedBinding(camera, "simulatedScale", simulatedScale, "text", doubleConverter);
        addWrappedBinding(camera, "simulatedFlipped", simulatedFlipped, "selected");

        addWrappedBinding(camera, "sourceUri", textFieldSourceUrl, "text");

        MutableLocationProxy imageUnitsPerPixel = new MutableLocationProxy();
        bind(UpdateStrategy.READ_WRITE, camera, "imageUnitsPerPixel", imageUnitsPerPixel, "location");
        addWrappedBinding(imageUnitsPerPixel, "lengthX", imageUnitsPerPixelX, "text", uppConverter);
        addWrappedBinding(imageUnitsPerPixel, "lengthY", imageUnitsPerPixelY, "text", uppConverter);

        ComponentDecorators.decorateWithAutoSelect(width);
        ComponentDecorators.decorateWithAutoSelect(height);
        ComponentDecorators.decorateWithAutoSelect(simulatedRotation);
        ComponentDecorators.decorateWithAutoSelect(simulatedScale);

        ComponentDecorators.decorateWithAutoSelect(textFieldSourceUrl);
        ComponentDecorators.decorateWithAutoSelect(imageUnitsPerPixelX);
        ComponentDecorators.decorateWithAutoSelect(imageUnitsPerPixelY);
    }

    private Action browseAction = new AbstractAction() {
        {
            putValue(NAME, "Browse");
            putValue(SHORT_DESCRIPTION, "Browse");
        }

        public void actionPerformed(ActionEvent e) {
            FileDialog fileDialog = new FileDialog((Frame) getTopLevelAncestor());
            fileDialog.setFilenameFilter(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    String[] extensions = new String[] {".png", ".jpg", ".gif", ".tif", ".tiff"};
                    for (String extension : extensions) {
                        if (name.toLowerCase().endsWith(extension)) {
                            return true;
                        }
                    }
                    return false;
                }
            });
            fileDialog.setVisible(true);
            if (fileDialog.getFile() == null) {
                return;
            }
            File file = new File(new File(fileDialog.getDirectory()), fileDialog.getFile());
            textFieldSourceUrl.setText(file.toURI().toString());
        }
    };
    private JLabel lblWidth;
    private JLabel lblHeight;
    private JTextField width;
    private JTextField height;
    private JLabel label;
    private JLabel lblUnitsPerPixel;
    private JTextField imageUnitsPerPixelX;
    private JTextField imageUnitsPerPixelY;
    private JLabel label_1;
    private JLabel lblScale;
    private JTextField simulatedScale;
    private JLabel lblX;
    private JLabel lblY;

    @Override
    protected void saveToModel() {
        super.saveToModel();
        UiUtils.messageBoxOnException(() -> {
            camera.reinitialize(); 
        });
    }
}
