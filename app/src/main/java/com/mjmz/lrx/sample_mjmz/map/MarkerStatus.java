package com.mjmz.lrx.sample_mjmz.map;


import com.amap.api.maps2d.model.Marker;
import com.amap.api.services.cloud.CloudItem;
import com.mjmz.lrx.sample_mjmz.R;

public class MarkerStatus {

	private CloudItem mCloudItem;
	private Boolean mIsPressed = false;
	private int mResPressed;
	private int mResUnPressed;
	private int mIndex;
	private Marker mMarker;


	public MarkerStatus(int i) {
		mIndex = i;
		setResBasedonIndex();

	}

	public void pressStatusToggle() {
		mIsPressed=!mIsPressed;
	}

	public void setCloudItem(CloudItem cloudItem) {
		// TODO Auto-generated method stub
		mCloudItem = cloudItem;
	}

	public CloudItem getCloudItem() {
		return mCloudItem;
	}

	public void setMarker(Marker marker) {
		// TODO Auto-generated method stub
		mMarker = marker;
	}

	public Marker getMarker() {
		return mMarker;
	}

	private void setResBasedonIndex() {
		switch (mIndex) {
		case 0:
			setmResPressed(R.drawable.poi_marker_pressed_1);
			setmResUnPressed(R.drawable.poi_marker_1);
			break;
		case 1:
			setmResPressed(R.drawable.poi_marker_pressed_2);
			setmResUnPressed(R.drawable.poi_marker_2);
			break;
		case 2:
			setmResPressed(R.drawable.poi_marker_pressed_3);
			setmResUnPressed(R.drawable.poi_marker_3);
			break;
		case 3:
			setmResPressed(R.drawable.poi_marker_pressed_4);
			setmResUnPressed(R.drawable.poi_marker_4);
			break;
		case 4:
			setmResPressed(R.drawable.poi_marker_pressed_5);
			setmResUnPressed(R.drawable.poi_marker_5);
			break;
		case 5:
			setmResPressed(R.drawable.poi_marker_pressed_6);
			setmResUnPressed(R.drawable.poi_marker_6);
			break;
		case 6:
			setmResPressed(R.drawable.poi_marker_pressed_7);
			setmResUnPressed(R.drawable.poi_marker_7);
			break;
		case 7:
			setmResPressed(R.drawable.poi_marker_pressed_8);
			setmResUnPressed(R.drawable.poi_marker_8);
			break;
		case 8:
			setmResPressed(R.drawable.poi_marker_pressed_9);
			setmResUnPressed(R.drawable.poi_marker_9);
			break;
		case 9:
			setmResPressed(R.drawable.poi_marker_pressed_10);
			setmResUnPressed(R.drawable.poi_marker_10);
			break;
		default:
			setmResPressed(R.drawable.poi_marker_pressed);
			setmResUnPressed(R.drawable.poi_marker);
			break;

		}

	}

	public int getmResUnPressed() {
		return mResUnPressed;
	}

	public void setmResUnPressed(int mResUnPressed) {
		this.mResUnPressed = mResUnPressed;
	}

	public int getmResPressed() {
		return mResPressed;
	}

	public void setmResPressed(int mResPressed) {
		this.mResPressed = mResPressed;
	}

	public Boolean getIsPressed() {
		return mIsPressed;
	}



}
