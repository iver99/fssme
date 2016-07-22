package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

public class IdGenerator {

	public static BigInteger getIntUUID(UUID uuid)
	{
		UUID id = uuid == null ? UUID.randomUUID() : uuid;
		return IdGenerator.toBigInteger(id);
	}

	private static BigInteger toBigInteger(UUID randomUUID)
	{
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(randomUUID.getMostSignificantBits());
		bb.putLong(randomUUID.getLeastSignificantBits());
		return new BigInteger(1, bb.array());
	}

}
