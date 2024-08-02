/**
 * <copyright>
 *
 * Copyright (c) 2012 Springsite BV (The Netherlands) and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Taal - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: EntityManagerProvider.java,v 1.7 2011/09/26 19:48:10 mtaal Exp $
 */
package org.eclipse.emf.texo.component;

/**
 * Interface to flag a class which has a static setInstance/getInstance method which can be used by an external software
 * to set a specific implementation of a component.
 * 
 * @see ComponentProvider
 * @see TexoComponent
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public interface TexoStaticSingleton {
}
