dataSource {
    pooled = true   
	properties {
		validationQuery = 'select 1'
	}
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:mem:testDb"
			driverClassName = "org.hsqldb.jdbcDriver"
			username = "sa"
			password = ""
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:hsqldb:mem:testDb"
			driverClassName = "org.hsqldb.jdbcDriver"
			username = "sa"
			password = ""
        }
    }
    production {
        dataSource {//messenger.opendream.org
			pooled = true
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/openmessenger?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8" 
			driverClassName = "com.mysql.jdbc.Driver"
			username = "openmessenger"
			password = "openpubyesroti!"
        }
    }
}
