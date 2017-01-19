package com.mycompany.a3;

import com.codename1.ui.geom.Point;

public interface ISelectable {
	public void setSelected(boolean s);
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
	public boolean isSelected();
	//public void draw(Graphics g, Point pCmpRelPrnt);
}
