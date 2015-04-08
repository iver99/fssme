/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.updatesearch.test;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.FileUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class FileUtilTest
{
	private static final String FILE_NAME = "tmpfile";
	private static final String FILE_DATA = "Testing Data";

	@Test
	public void fileTest()
	{
		try {

			FileUtils.createOutputfile(FILE_NAME, FILE_DATA);
			String tmp = FileUtils.readFile(FILE_NAME);
			if (FileUtils.fileExist(FILE_NAME)) {
				FileUtils.deleteFile(FILE_NAME);
			}
			Assert.assertEquals(tmp, FILE_DATA);
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

}
