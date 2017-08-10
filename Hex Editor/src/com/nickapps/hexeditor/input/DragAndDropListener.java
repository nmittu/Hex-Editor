package com.nickapps.hexeditor.input;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import com.nickapps.hexeditor.HexEditor;
import com.nickapps.hexeditor.gui.TextAreaPanel;
import com.nickapps.hexeditor.util.fileIO.TxtFile;

public class DragAndDropListener implements DropTargetListener {

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		// TODO Auto-generated method stub

		// Accept copy drops
		dtde.acceptDrop(DnDConstants.ACTION_COPY);

		// Get the transfer which can provide the dropped item data
		Transferable transferable = dtde.getTransferable();

		transferable.getTransferDataFlavors();

		// Loop through the flavors

			try {

				// If the drop items are files

				// Get all of the dropped files
				@SuppressWarnings("unchecked")
				List<File> files = (List<File>) transferable
						.getTransferData(DataFlavor.javaFileListFlavor);

				// Loop them through
				for (File file : files) {

					// Print out the file path
					HexEditor.frame.tabbedPane.open(file.getAbsolutePath());

				}

			} catch (Exception e) {

				// Print out the error stack
				e.printStackTrace();

		}

		// Inform that the drop is complete
		dtde.dropComplete(true);

	}

}
