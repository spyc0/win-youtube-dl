/* 
 * Copyright 2013 Fredy Wijaya
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.fredy.winyoutubedl;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A main GUI class.
 * 
 * @author fredy
 */
public class WinYouTubeDownloader {
    private Text urlText;
    private Text folderText;
    private Button openButton;
    private Button toMP3Checkbox;
    private Button downloadButton;
    private Button updateButton;
    
    public WinYouTubeDownloader() {
        Display display = new Display();
        Shell shell = new Shell(display,
            SWT.TITLE | SWT.CLOSE | SWT.BORDER & (~SWT.RESIZE));
        shell.setText("WinYouTubeDownloader");
        shell.setLayout(new GridLayout(1, false));
        
        createContent(shell);
        
        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
    
    private void createContent(final Shell parent) {
        Composite c1 = new Composite(parent, SWT.BORDER);
        c1.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        c1.setLayout(new GridLayout(3, false));
        
        Label urlLabel = new Label(c1, SWT.NONE);
        urlLabel.setText("URL:");
        urlText = new Text(c1, SWT.BORDER);
        urlText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        urlText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!urlText.getText().isEmpty()) {
                    downloadButton.setEnabled(true);
                } else {
                    downloadButton.setEnabled(false);
                }
                parent.pack();
            }
        });
        toMP3Checkbox = new Button(c1, SWT.CHECK);
        toMP3Checkbox.setText("To MP3");
        toMP3Checkbox.setSelection(Settings.toMP3());
        toMP3Checkbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    Settings.setToMP3(toMP3Checkbox.getSelection());
                } catch (Exception ex) {
                    MessageBox mb = new MessageBox(parent, SWT.ICON_ERROR);
                    mb.setText("Error");
                    mb.setMessage(ex.getMessage());
                    mb.open();
                }
            }
        });

        Label folderLabel = new Label(c1, SWT.NONE);
        folderLabel.setText("Folder:");
        folderText = new Text(c1, SWT.BORDER);
        folderText.setEditable(false);
        folderText.setText(Settings.getDirectory().getAbsolutePath());
        folderText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        openButton = new Button(c1, SWT.PUSH);
        openButton.setText("Open...");
        final DirectoryDialog directoryDialog = new DirectoryDialog(parent, SWT.OPEN);
        openButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                String directory = directoryDialog.open();
                if (directory != null) {
                    folderText.setText(directory);
                    Settings.setDirectory(new File(directory));
                    parent.pack();
                }
            }
        });
        
        downloadButton = new Button(c1, SWT.PUSH);
        downloadButton.setEnabled(false);
        GridData gridData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gridData.horizontalSpan = 3;
        downloadButton.setLayoutData(gridData);
        downloadButton.setText("Download");
        downloadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                try {
                    if (!folderText.getText().startsWith("http://")
                        && !folderText.getText().startsWith("https://")) {
                        MessageBox mb = new MessageBox(parent, SWT.ICON_ERROR);
                        mb.setText("Error");
                        mb.setMessage("The URL must start with http:// or https://");
                        mb.open();
                        return;
                    }
                    YouTubeDownloader.download(
                        folderText.getText(),
                        urlText.getText().trim(),
                        toMP3Checkbox.getSelection());
                } catch (Exception ex) {
                    MessageBox mb = new MessageBox(parent, SWT.ICON_ERROR);
                    mb.setText("Error");
                    mb.setMessage(ex.getMessage());
                    mb.open();
                }
            }
        });
        
        Composite c2 = new Composite(parent, SWT.BORDER);
        c2.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        c2.setLayout(new GridLayout(2, false));
        
        updateButton = new Button(c2, SWT.PUSH);
        updateButton.setText("Update");
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                try {
                    YouTubeDownloader.update();
                } catch (Exception ex) {
                    MessageBox mb = new MessageBox(parent, SWT.ICON_ERROR);
                    mb.setText("Error");
                    mb.setMessage(ex.getMessage());
                    mb.open();
                }
            }
        });
        Label authorLabel = new Label(c2, SWT.NONE);
        authorLabel.setLayoutData(new GridData(SWT.NONE, SWT.CENTER, false, false));
        authorLabel.setText("Created by Fredy Wijaya");
    }
    
    public static void main(String[] args) {
        new WinYouTubeDownloader();
    }
}
