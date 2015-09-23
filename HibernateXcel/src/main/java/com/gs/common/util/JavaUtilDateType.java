package com.gs.common.util;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.LiteralType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.VersionType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor;
import org.hibernate.type.descriptor.sql.TimestampTypeDescriptor;

/**
 * Note: Depends on hibernate implementation details hibernate-core-4.3.6.Final.
 * 
 * @see <a
 *      href="http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch06.html#types-custom">Hibernate
 *      Documentation</a>
 * @see TimestampType
 */
public class JavaUtilDateType extends
		AbstractSingleColumnStandardBasicType<Date> implements
		VersionType<Date>, LiteralType<Date> {

	public static final TimestampType INSTANCE = new TimestampType();

	public JavaUtilDateType() {
		super(TimestampTypeDescriptor.INSTANCE,
				new JdbcTimestampTypeDescriptor() {

					@Override
					public Date fromString(String string) {
						return new Date(super.fromString(string).getTime());
					}

					@Override
					public <X> Date wrap(X value, WrapperOptions options) {
						if(null == value){return null;}
						return new Date(super.wrap(value, options).getTime());
					}

				});
	}

	@Override
	public String getName() {
		return "timestamp";
	}

	@Override
	public String[] getRegistrationKeys() {
		return new String[] { getName(), Timestamp.class.getName(),
				java.util.Date.class.getName() };
	}

	@Override
	public Date next(Date current, SessionImplementor session) {
		return seed(session);
	}

	@Override
	public Date seed(SessionImplementor session) {
		return new Timestamp(System.currentTimeMillis());
	}

	@Override
	public Comparator<Date> getComparator() {
		return getJavaTypeDescriptor().getComparator();
	}

	@Override
	public String objectToSQLString(Date value, Dialect dialect)
			throws Exception {
		final Timestamp ts = Timestamp.class.isInstance(value) ? (Timestamp) value
				: new Timestamp(value.getTime());
		// TODO : use JDBC date literal escape syntax? -> {d 'date-string'} in
		// yyyy-mm-dd hh:mm:ss[.f...] format
		return StringType.INSTANCE.objectToSQLString(ts.toString(), dialect);
	}

	@Override
	public Date fromStringValue(String xml) throws HibernateException {
		return fromString(xml);
	}
}