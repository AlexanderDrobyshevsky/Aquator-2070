package ru.vermilion.auxiliary;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import ru.vermilion.basic.CommonHelper;

public class MessageOKCancelDialog {

    public enum DialogCase {OK, CANCEL;}

    private String title;
    private String inviteText;
    private Shell shell;

    private static Color backgroundColor;

    public MessageOKCancelDialog(Shell shell, String title, String inviteText) {
        this.title = title;
        this.inviteText = inviteText;
        this.shell = shell;

        backgroundColor = shell.getDisplay().getSystemColor(SWT.COLOR_WHITE);
    }

    public DialogCase open() {
        Shell dialog = createContent();

        CommonHelper.centerShell(shell, dialog);

        dialog.open();

        while (!dialog.isDisposed()) {
            if (!dialog.getDisplay().readAndDispatch())
                dialog.getDisplay().sleep();
        }

        if (!dialog.isDisposed()) {
            dialog.close();
        }

        return dialogOutput[0];
    }

    private DialogCase dialogOutput[] = new DialogCase[]{DialogCase.CANCEL};

    private Shell createContent() {
        final Shell dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        dialog.setText(title);
        dialog.setBackground(backgroundColor);

        GridLayout gl = new GridLayout(1, false);
        gl.horizontalSpacing = 10;
        gl.verticalSpacing = 0;
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        dialog.setLayout(gl);

        final Composite textClient = new Composite(dialog, SWT.NONE);

        gl = new GridLayout(1, false);
        gl.horizontalSpacing = 0;
        gl.verticalSpacing = 0;
        gl.marginHeight = 0;
        gl.marginWidth = 15;
        textClient.setLayout(gl);

        textClient.setBackground(backgroundColor);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.minimumHeight = 50;
        gd.minimumWidth = 200;
        textClient.setLayoutData(gd);

        Label messageLabel = new Label(textClient, SWT.BOLD);
        messageLabel.setBackground(backgroundColor);
        messageLabel.setText(inviteText);

        gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
        messageLabel.setLayoutData(gd);

        final Composite buttonsClient = new Composite(dialog, SWT.NONE);

        gl = new GridLayout(2, false);
        gl.horizontalSpacing = 20;
        gl.verticalSpacing = 10;
        gl.marginHeight = 10;
        gl.marginWidth = 10;
        buttonsClient.setLayout(gl);

        buttonsClient.setBackground(backgroundColor);

        gd = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
        gd.minimumHeight = 50;
        gd.minimumWidth = 200;
        buttonsClient.setLayoutData(gd);

        Button OK = new Button(buttonsClient, SWT.PUSH);
        OK.setText("     OK     ");
        OK.setBackground(backgroundColor);

        GridData okGridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        OK.setLayoutData(okGridData);
        OK.setFocus();
        OK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                dialogOutput[0] = DialogCase.OK;

                dialog.close();
            }
        });

        Button cancel = new Button(buttonsClient, SWT.PUSH);
        cancel.setText("     Cancel     ");
        cancel.setBackground(backgroundColor);

        GridData cancelGridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        cancel.setLayoutData(cancelGridData);
        cancel.setFocus();
        cancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                dialogOutput[0] = DialogCase.CANCEL;

                dialog.close();
            }
        });

        dialog.setDefaultButton(cancel);

        dialog.pack();
        cancel.setFocus();

        return dialog;
    }

}
