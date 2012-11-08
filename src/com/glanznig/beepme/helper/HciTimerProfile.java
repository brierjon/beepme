/*
This file is part of BeepMe.

BeepMe is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

BeepMe is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with BeepMe. If not, see <http://www.gnu.org/licenses/>.

Copyright since 2012 Michael Glanznig
http://beepme.glanznig.com
*/

package com.glanznig.beepme.helper;

import com.glanznig.beepme.data.StorageHandler;

public class HciTimerProfile extends TimerProfile {
	
	//all times in seconds
	private static final int avgBeepInterval = 420; //7 min
	private static final int maxBeepInterval = 900; //15 min
	private static final int minBeepInterval = 180; //3 min
	
	public HciTimerProfile(StorageHandler datastore) {
		super(datastore);
	}

	@Override
	public int getTimer() {
		// TODO Auto-generated method stub
		return 0;
	}

}
