/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.curator.framework.recipes.watch;

import java.util.Arrays;

/**
 * Pre-built node comparators for common uses
 */
public class CachedNodeComparators
{
    private static final CachedNodeComparator dataOnly = new CachedNodeComparator()
    {
        @Override
        public boolean isSame(CachedNode n1, CachedNode n2)
        {
            return firstCheck(n1, n2) && Arrays.equals(n1.getData(), n2.getData());
        }
    };

    private static final CachedNodeComparator dataAndType = new CachedNodeComparator()
    {
        @Override
        public boolean isSame(CachedNode n1, CachedNode n2)
        {
            return firstCheck(n1, n2) && Arrays.equals(n1.getData(), n2.getData()) && sameType(n1.getStat().getEphemeralOwner(), n2.getStat().getEphemeralOwner());
        }
    };

    private static final CachedNodeComparator deep = new CachedNodeComparator()
    {
        @Override
        public boolean isSame(CachedNode n1, CachedNode n2)
        {
            return n1.equals(n2);
        }
    };

    private static boolean sameType(long e1, long e2)
    {
        boolean e1IsEphemeral = (e1 > 0);
        boolean e2IsEphemeral = (e2 > 0);
        return e1IsEphemeral == e2IsEphemeral;
    }

    private static boolean firstCheck(CachedNode n1, CachedNode n2)
    {
        if ( n1 == n2 )
        {
            return true;
        }
        //noinspection RedundantIfStatement
        if ( (n1 == null) || (n2 == null) )
        {
            return false;
        }
        return true;
    }

    /**
     * Returns a comparator that only compares the byte array of the node, ignoring the Stat
     *
     * @return data only comparator
     */
    public static CachedNodeComparator dataOnly()
    {
        return dataOnly;
    }

    /**
     * Returns a comparator that compares the byte array of the node, and the ephemeralOwner
     * of the Stat. i.e. the data must match and the ephemeral type must be the same
     *
     * @return data and type comparator
     */
    public static CachedNodeComparator dataAndType()
    {
        return dataAndType;
    }

    /**
     * Returns a comparator that compares the byte array of the node and the stat
     *
     * @return deep comparator
     */
    public static CachedNodeComparator deep()
    {
        return deep;
    }

    private CachedNodeComparators()
    {
    }
}
