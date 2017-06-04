/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.geofencing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuanwang on 5/20/17.
 */

public class GeofencePlaceDict {
  private static final Object lock = new Object();
  private static volatile GeofencePlaceDict instance;

  public Map<String, String> dict;

  public GeofencePlaceDict(){
    dict = new HashMap<>();
  }

  public static GeofencePlaceDict getInstance() {
    GeofencePlaceDict r = instance;
    if (r == null) {
      synchronized (lock) {    // While we were waiting for the lock, another
        r = instance;        // thread may have instantiated the object.
        if (r == null) {
          r = new GeofencePlaceDict();
          instance = r;
        }
      }
    }
    return r;
  }
}
