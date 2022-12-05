plugins {
	java
	groovy
	id("org.springframework.boot") version "3.0.0"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "cx.kaminski"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
	testImplementation("org.spockframework:spock-spring:2.3-groovy-4.0")
	testRuntimeOnly("net.bytebuddy:byte-buddy:1.12.19")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
