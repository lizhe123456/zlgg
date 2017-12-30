/**  
 * Project Name:Android_Car_Example  
 * File Name:RegeocodeTask.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月2日下午6:24:53  
 *  
 */

package com.zlcm.zlgg.lib;

import android.content.Context;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

/**
 * ClassName:RegeocodeTask <br/>
 * Function: 简单的封装的逆地理编码功能 <br/>
 * Date: 2015年4月2日 下午6:24:53 <br/>
 * 
 * @author yiyi.qi
 * @version
 * @since JDK 1.6
 * @see
 */
public class RegeocodeTask implements OnGeocodeSearchListener {
	private static final float SEARCH_RADIUS = 50;
	private OnRegecodeGetListenter onRegecodeGetListener;

	private GeocodeSearch mGeocodeSearch;

	public RegeocodeTask(Context context) {
		mGeocodeSearch = new GeocodeSearch(context);
		mGeocodeSearch.setOnGeocodeSearchListener(this);
	}

	public void search(double latitude, double longitude) {
		RegeocodeQuery regecodeQuery = new RegeocodeQuery(new LatLonPoint(
				latitude, longitude), SEARCH_RADIUS, GeocodeSearch.AMAP);
		mGeocodeSearch.getFromLocationAsyn(regecodeQuery);
	}

	public void setOnRegecodeGetListener(OnRegecodeGetListenter onRegecodeGetListener) {
		this.onRegecodeGetListener = onRegecodeGetListener;
	}

	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {

	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult regeocodeReult,
			int resultCode) {
		if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
			if (regeocodeReult != null
					&& regeocodeReult.getRegeocodeAddress() != null
					&& onRegecodeGetListener != null) {
				String address = regeocodeReult.getRegeocodeAddress()
						.getFormatAddress();
				String city = regeocodeReult.getRegeocodeAddress().getCityCode();
		 
				PositionEntity entity = new PositionEntity();
				entity.address = address;
				entity.city = city;
				onRegecodeGetListener.onRegecodeGet(entity);

			}
		}
	}

}
