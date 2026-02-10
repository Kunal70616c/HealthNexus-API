# HealthNexus API - Complete Documentation & Learning Reference

A **secure Spring Boot microservice** for healthcare management with OAuth2 authentication, service discovery, and comprehensive patient/doctor/address management capabilities.

> 📚 **Purpose:** This repository serves as a comprehensive learning documentation for building enterprise-grade Spring Boot microservices with security, persistence, and best practices.

---

## 📑 Table of Contents

1. [Project Structure](#-project-structure)
2. [Tech Stack & Dependencies](#-tech-stack--dependencies)
3. [Configuration Files Explained](#-configuration-files-explained)
4. [Domain Models (Entities)](#-domain-models-entities)
5. [Repository Layer](#-repository-layer)
6. [Service Layer](#-service-layer)
7. [Exception Handling](#-exception-handling)
8. [DTOs & Mappers](#-dtos--mappers)
9. [Controllers (REST APIs)](#-controllers-rest-apis)
10. [Security Configuration](#-security-configuration)
11. [API Documentation (Swagger)](#-api-documentation-swagger)
12. [Annotations Reference](#-annotations-reference)
13. [Design Patterns Used](#-design-patterns-used)
14. [Running the Application](#-running-the-application)

---

## 🏗️ Project Structure

```
sh.surge.kunal.healthnexus/
├── configurations/    # Security, Eureka, OpenAPI, and Bean configurations
├── controllers/       # REST API Endpoints with role-based access
├── dtos/              # Data Transfer Objects for API requests/responses
├── exceptions/        # Custom exceptions and Global Exception Handling
├── facades/           # Custom annotations (AdhaarCardId generator)
├── mappers/           # Entity ↔ DTO conversion using MapStruct
├── models/            # JPA Entities with inheritance strategy
├── repositories/      # Spring Data JPA Repositories
├── services/          # Business logic implementation layer
└── HealthNexusAPI     # Main Spring Boot Application class

resources/
└── application.properties    # MySQL, Keycloak, Eureka, Swagger configs
```

### **Package Responsibilities**

| Package | Purpose | Key Technologies |
|---------|---------|------------------|
| `models` | Domain entities representing database tables | JPA, Hibernate, Lombok |
| `repositories` | Data access layer with query methods | Spring Data JPA |
| `services` | Business logic and transaction management | @Service, @Transactional |
| `controllers` | REST endpoints and request handling | @RestController, @RequestMapping |
| `dtos` | Data transfer objects for API contracts | Records, Lombok, Validation |
| `mappers` | Entity ↔ DTO conversion | MapStruct |
| `exceptions` | Custom exceptions and global error handling | @ControllerAdvice |
| `configurations` | Security, Swagger, and app config | Spring Security, OpenAPI |
| `facades` | Custom annotations and utilities | Hibernate SPI |

---

## 🔧 Tech Stack & Dependencies

### **Core Framework**

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17+ | Programming language |
| Spring Boot | 3.x | Application framework |
| Maven | - | Dependency management |

### **Persistence Layer**

| Technology | Purpose | Configuration |
|------------|---------|---------------|
| Spring Data JPA | ORM and repository abstraction | `spring-boot-starter-data-jpa` |
| Hibernate | JPA implementation | Auto-configured |
| MySQL | Relational database | Port 3306, database: `hospital_management_db` |
| HikariCP | Connection pooling | Min: 5, Max: 12, Timeout: 20s |

### **Security**

| Technology | Purpose | Configuration |
|------------|---------|---------------|
| Spring Security | Authentication & authorization | `spring-boot-starter-security` |
| OAuth2 Resource Server | JWT token validation | Keycloak issuer URL |
| Keycloak | Identity and access management | `localhost:8080/realms/master` |

### **Service Discovery**

| Technology | Purpose | Configuration |
|------------|---------|---------------|
| Eureka Client | Microservice registration | Port 8761, instance ID with random value |

### **API Documentation**

| Technology | Purpose | Configuration |
|------------|---------|---------------|
| SpringDoc OpenAPI | Swagger UI generation | OAuth2 security schemes |
| Swagger UI | Interactive API testing | Available at `/swagger-ui.html` |

### **Utilities**

| Library | Purpose | Usage |
|---------|---------|-------|
| Lombok | Boilerplate reduction | @Data, @Builder, @NoArgsConstructor |
| MapStruct | DTO mapping | Compile-time code generation |
| JavaFaker | Test data generation | Aadhaar card ID generation |
| Jakarta Validation | Bean validation | @NotNull, @Pattern, @Valid |

---

## ⚙️ Configuration Files Explained

### **application.properties Breakdown**

```properties
# ============================================
# APPLICATION CONFIGURATION
# ============================================
spring.application.name=hospitalmangementapi
server.port=7076

# ============================================
# DATABASE CONFIGURATION
# ============================================
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_management_db
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

| Property | Value | Explanation |
|----------|-------|-------------|
| `spring.application.name` | `hospitalmangementapi` | Application name used by Eureka for service registration |
| `server.port` | `7076` | Port where the application runs |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/hospital_management_db` | MySQL connection URL |
| `spring.datasource.driver-class-name` | `com.mysql.cj.jdbc.Driver` | MySQL JDBC driver |

#### **HikariCP Connection Pool Settings**

```properties
# ============================================
# CONNECTION POOL CONFIGURATION
# ============================================
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.maximum-pool-size=12
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=1200000
```

| Property | Value | Explanation |
|----------|-------|-------------|
| `connection-timeout` | 20000ms (20s) | Maximum time to wait for a connection from the pool |
| `minimum-idle` | 5 | Minimum number of idle connections maintained |
| `maximum-pool-size` | 12 | Maximum number of connections in the pool |
| `idle-timeout` | 300000ms (5min) | Maximum time a connection can sit idle |
| `max-lifetime` | 1200000ms (20min) | Maximum lifetime of a connection in the pool |

#### **JPA/Hibernate Configuration**

```properties
# ============================================
# JPA & HIBERNATE CONFIGURATION
# ============================================
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

| Property | Value | Explanation |
|----------|-------|-------------|
| `database-platform` | `MySQL8Dialect` | Tells Hibernate which SQL dialect to use |
| `generate-ddl` | `true` | Generate DDL statements (CREATE, ALTER) |
| `ddl-auto` | `update` | Update schema without dropping existing data |
| `show-sql` | `true` | Print SQL statements to console (development only) |
| `naming.implicit-strategy` | `ImplicitNamingStrategyLegacyJpaImpl` | Uses JPA 1.0 naming (property names as-is) |
| `naming.physical-strategy` | `PhysicalNamingStrategyStandardImpl` | No transformation of names |

**⚠️ DDL-AUTO Options:**

| Value | Behavior | Use Case |
|-------|----------|----------|
| `none` | Do nothing | Production |
| `validate` | Validate schema, no changes | Production |
| `update` | Update schema if needed | Development |
| `create` | Drop and create schema on startup | Testing |
| `create-drop` | Create on startup, drop on shutdown | Testing |

#### **OAuth2 Resource Server Configuration**

```properties
# ============================================
# OAUTH2 & KEYCLOAK CONFIGURATION
# ============================================
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/realms/master
keycloak.issuer=http://localhost:8080/realms/master
keycloak.realm=master
springdoc.swagger-ui.oauth.use-pkce-with-authorization-code-grant=true
```

| Property | Value | Explanation |
|----------|-------|-------------|
| `jwt.issuer-uri` | Keycloak realm URL | Validates JWT tokens against this issuer |
| `keycloak.issuer` | Same as above | Used by OpenAPI config for OAuth flows |
| `keycloak.realm` | `master` | Keycloak realm name |
| `oauth.use-pkce-with-authorization-code-grant` | `true` | Enables PKCE for security (prevents CSRF) |

#### **Eureka Client Configuration**

```properties
# ============================================
# EUREKA CLIENT CONFIGURATION
# ============================================
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true
eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=false
eureka.instance.lease-renewal-interval-in-seconds=10
eureka.instance.lease-expiration-duration-in-seconds=30
```

| Property | Value | Explanation |
|----------|-------|-------------|
| `service-url.defaultZone` | `http://localhost:8761/eureka/` | Eureka server location |
| `prefer-ip-address` | `true` | Register with IP instead of hostname |
| `instance-id` | `app-name:random-value` | Unique instance identifier |
| `register-with-eureka` | `true` | Register this service with Eureka |
| `fetch-registry` | `false` | Don't fetch other services' info |
| `lease-renewal-interval` | 10s | Heartbeat interval |
| `lease-expiration-duration` | 30s | Time before instance is removed if no heartbeat |

---

## 📦 Domain Models (Entities)

### **Entity Inheritance Hierarchy**

```
Person (Abstract)
├── Patient
└── Doctor
```

### **Person Entity (Abstract Base Class)**

```java
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class Person implements Serializable {
    @Id
    @Column(name = "adhaar_card_no", nullable = false, length = 12)
    @AdhaarCardId
    private String adhaarCardNo;
    
    @Embedded
    private FullName fullName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 10)
    private Gender gender;
    
    @Column(name = "date_of_birth", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    
    @Column(name = "contact_number")
    private long contactNumber;
    
    @Column(name = "email", length = 150, unique = true, nullable = true)
    private String email;
}
```

#### **Annotations Explained**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Entity` | Marks class as JPA entity (mapped to database table) | Every domain model |
| `@Table(name = "person")` | Specifies table name in database | Custom table naming |
| `@Inheritance(strategy = InheritanceType.JOINED)` | Defines inheritance strategy | Creates separate tables for Person, Patient, Doctor with FK relationships |
| `@SuperBuilder` | Lombok annotation for builder pattern with inheritance | Allows `Patient.builder().fullName(...).ailment(...).build()` |
| `@Id` | Marks field as primary key | `adhaarCardNo` |
| `@Column` | Customizes column mapping | `nullable`, `length`, `unique` constraints |
| `@Embedded` | Embeds another class as columns in same table | `FullName` stored as `first_name`, `last_name` in Person table |
| `@Enumerated(EnumType.STRING)` | Stores enum as string in DB (not ordinal) | Gender stored as "MALE", "FEMALE", "OTHER" |
| `@DateTimeFormat` | Spring annotation for date formatting | ISO date format |

#### **Inheritance Strategies Comparison**

| Strategy | Tables Created | Performance | Use Case |
|----------|----------------|-------------|----------|
| `SINGLE_TABLE` | One table with discriminator column | Fast (no joins) | Simple hierarchies, few subclass-specific fields |
| `TABLE_PER_CLASS` | One table per concrete class | Slow (UNION queries) | Rarely used |
| `JOINED` ⭐ | One table per class with FK joins | Moderate | Normalized data, many subclass-specific fields |

**Why JOINED for this project?**
- Patient has specific fields (ailment, occupation)
- Doctor has specific fields (licenseNumber, specialization, qualification)
- Normalized design prevents NULL columns

---

### **Patient Entity**

```java
@Entity
@Table(name = "patient")
@SuperBuilder
public class Patient extends Person implements Serializable {
    @Column(name = "ailment", length = 100, nullable = false)
    private String ailment;
    
    @Column(name = "occupation", length = 100, nullable = true)
    private String occupation;
}
```

**Database Structure (JOINED Strategy):**

```sql
-- person table
CREATE TABLE person (
    adhaar_card_no VARCHAR(12) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    date_of_birth DATE NOT NULL,
    contact_number BIGINT,
    email VARCHAR(150) UNIQUE
);

-- patient table (child)
CREATE TABLE patient (
    adhaar_card_no VARCHAR(12) PRIMARY KEY,
    ailment VARCHAR(100) NOT NULL,
    occupation VARCHAR(100),
    FOREIGN KEY (adhaar_card_no) REFERENCES person(adhaar_card_no)
);
```

---

### **Doctor Entity**

```java
@Entity
@Table(name = "doctor")
@SuperBuilder
public class Doctor extends Person implements Serializable {
    @Column(name = "license_number", length = 50, nullable = false, unique = true)
    private String licenseNumber;
    
    @Column(name = "specialization", length = 100, nullable = false)
    private String specialization;
    
    @Column(name = "qualification", length = 100, nullable = false)
    private String qualification;
}
```

---

### **Address Entity (Relationship Example)**

```java
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "house_number", nullable = false, length = 10)
    private String houseNumber;
    
    @Column(name = "street_name", nullable = false, length = 100)
    private String streetName;
    
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    
    @Column(name = "state", nullable = false, length = 50)
    private String state;
    
    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;
    
    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "adhaarCardNo"), name = "person_adhaar_card_no")
    private Person person;
}
```

#### **Relationship Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@ManyToOne` | Many addresses can belong to one person | Person can have multiple addresses |
| `optional = false` | Person is required (NOT NULL FK) | Cannot create address without person |
| `cascade = CascadeType.MERGE` | Merge operations cascade to person | Updating address updates person if needed |
| `fetch = FetchType.LAZY` | Don't load person until accessed | Performance optimization |
| `@JoinColumn` | Specifies FK column name | `person_adhaar_card_no` in address table |
| `@ForeignKey` | Names the FK constraint | Constraint name in database |

#### **Cascade Types**

| Type | Behavior | Use Case |
|------|----------|----------|
| `PERSIST` | Save child when parent is saved | Creating new entities |
| `MERGE` | Update child when parent is updated | Updating entities |
| `REMOVE` | Delete child when parent is deleted | Parent-child dependency |
| `REFRESH` | Reload child when parent is reloaded | Syncing with DB |
| `DETACH` | Detach child when parent is detached | Session management |
| `ALL` | All of the above | Strong parent-child relationship |

#### **Fetch Types**

| Type | Behavior | Performance | Use Case |
|------|----------|-------------|----------|
| `EAGER` | Load immediately with parent | Multiple queries | Small related data, always needed |
| `LAZY` ⭐ | Load only when accessed | Single query initially | Large data, conditionally needed |

---

### **Embeddable Components**

#### **FullName (Embeddable)**

```java
@Embeddable
@Builder
public class FullName {
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
}
```

| Annotation | Purpose | Result |
|------------|---------|--------|
| `@Embeddable` | Allows embedding in entity | Fields become columns in parent table |

**Without @Embeddable:**
```java
// Would need separate table
@OneToOne
private FullName fullName;
```

**With @Embeddable:**
```sql
-- Columns directly in person table
CREATE TABLE person (
    adhaar_card_no VARCHAR(12),
    first_name VARCHAR(50),    -- From FullName
    last_name VARCHAR(50),     -- From FullName
    gender VARCHAR(10)
);
```

---

### **Enums**

```java
public enum Gender {
    MALE, FEMALE, OTHER
}
```

**Storage in Database:**

| `@Enumerated` Type | Stored As | Example | Risk |
|-------------------|-----------|---------|------|
| `EnumType.ORDINAL` | Integer (0, 1, 2) | 0 = MALE | Breaks if enum order changes |
| `EnumType.STRING` ⭐ | String | "MALE" | Safe, readable |

---

### **Custom ID Generation**

#### **AdhaarCardIdGenerator**

```java
public class AdhaarCardIdGenerator implements BeforeExecutionGenerator {
    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, 
                          Object owner, Object currentValue, EventType eventType) {
        Faker faker = new Faker();
        if (currentValue != null) {
            return currentValue;
        } else {
            return faker.number().digits(12);
        }
    }
}
```

#### **Custom Annotation**

```java
@IdGeneratorType(AdhaarCardIdGenerator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdhaarCardId {
}
```

**How It Works:**

1. `@AdhaarCardId` annotation marks the field
2. Hibernate detects `@IdGeneratorType` and uses `AdhaarCardIdGenerator`
3. On `INSERT`, generates 12-digit random number using JavaFaker
4. If ID already set, keeps existing value

**Annotation Components:**

| Component | Purpose |
|-----------|---------|
| `@IdGeneratorType` | Links annotation to generator class |
| `@Target` | Specifies where annotation can be used (fields, methods, parameters) |
| `@Retention(RUNTIME)` | Annotation available at runtime via reflection |

---

## 🗄️ Repository Layer

### **Spring Data JPA Repositories**

#### **PersonRepository**

```java
public interface PersonRepository extends JpaRepository<Person, String> {
}
```

**What `JpaRepository` Provides:**

| Method | Description | SQL Equivalent |
|--------|-------------|----------------|
| `save(entity)` | Insert or update | `INSERT` or `UPDATE` |
| `findById(id)` | Find by primary key | `SELECT * WHERE id = ?` |
| `findAll()` | Get all records | `SELECT * FROM table` |
| `deleteById(id)` | Delete by primary key | `DELETE WHERE id = ?` |
| `existsById(id)` | Check if exists | `SELECT COUNT(*) WHERE id = ?` |
| `count()` | Count all records | `SELECT COUNT(*) FROM table` |

**Type Parameters:**
- `JpaRepository<Entity, ID>`
  - `Entity`: The domain class (Person, Patient, etc.)
  - `ID`: Primary key type (String for Aadhaar, Long for Address)

---

#### **PatientRepository (Custom Query Methods)**

```java
public interface PatientRepository extends JpaRepository<Patient, String> {
    Optional<Patient> findByEmail(String email);
}
```

**Spring Data JPA Query Derivation:**

| Method Name | Generated SQL | Explanation |
|-------------|---------------|-------------|
| `findByEmail(String email)` | `SELECT * FROM patient p WHERE p.email = ?` | Finds by exact email match |
| `findByEmailAndContactNumber(...)` | `WHERE email = ? AND contact_number = ?` | Multiple conditions |
| `findByAilmentContaining(String keyword)` | `WHERE ailment LIKE %?%` | Partial match |
| `findByDateOfBirthBefore(LocalDate date)` | `WHERE date_of_birth < ?` | Date comparison |

**Optional Return Type:**
- `Optional<Patient>` prevents `NullPointerException`
- Use `.orElseThrow()` to handle not found cases

```java
Patient patient = patientRepository.findByEmail("test@example.com")
    .orElseThrow(() -> new PatientNotFoundException("Not found"));
```

---

#### **AddressRepository (Complex Queries)**

```java
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByPersonAdhaarCardNo(String adhaarCardNo);
    Optional<Address> findByIdAndPersonAdhaarCardNo(Long id, String adhaarCardNo);
    boolean existsByIdAndPersonAdhaarCardNo(Long id, String adhaarCardNo);
}
```

**Query Method Naming Convention:**

| Keyword | Example | SQL |
|---------|---------|-----|
| `findBy` | `findByCity(String city)` | `WHERE city = ?` |
| `And` | `findByIdAndPersonAdhaarCardNo(...)` | `WHERE id = ? AND person_adhaar_card_no = ?` |
| `Or` | `findByCityOrState(...)` | `WHERE city = ? OR state = ?` |
| `Between` | `findByDateOfBirthBetween(...)` | `WHERE dob BETWEEN ? AND ?` |
| `LessThan` | `findByAgeLessThan(int age)` | `WHERE age < ?` |
| `GreaterThan` | `findByAgeGreaterThan(int age)` | `WHERE age > ?` |
| `Like` | `findByNameLike(String pattern)` | `WHERE name LIKE ?` |
| `Containing` | `findByNameContaining(String str)` | `WHERE name LIKE %?%` |
| `StartingWith` | `findByNameStartingWith(...)` | `WHERE name LIKE ?%` |
| `EndingWith` | `findByNameEndingWith(...)` | `WHERE name LIKE %?` |
| `OrderBy` | `findByStateOrderByCityAsc(...)` | `WHERE state = ? ORDER BY city ASC` |
| `exists` | `existsByEmail(String email)` | `SELECT COUNT(*) > 0 WHERE email = ?` |

**Navigating Relationships:**
- `PersonAdhaarCardNo` navigates `Person` → `adhaarCardNo`
- Syntax: `{RelationshipProperty}{PropertyOfRelatedEntity}`

---

### **Repository Method Return Types**

| Return Type | Use Case | Behavior |
|-------------|----------|----------|
| `Entity` | Single result expected | Throws exception if not found |
| `Optional<Entity>` | Single result, may not exist | Returns empty Optional if not found |
| `List<Entity>` | Multiple results | Returns empty list if none found |
| `boolean` | Existence check | Returns true/false |
| `long` | Count query | Returns number of records |

---

## 🔧 Service Layer

### **Service Annotations**

```java
@Service
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    // Methods...
}
```

| Annotation | Purpose | Impact |
|------------|---------|--------|
| `@Service` | Marks class as service component | Spring auto-detects and creates bean |
| `@Transactional(readOnly = true)` | All methods are read-only by default | Optimizes performance, prevents accidental writes |
| `@Autowired` | Dependency injection | Spring injects repository/entityManager instances |

---

### **Transaction Management**

#### **@Transactional Attributes**

```java
@Transactional(propagation = Propagation.SUPPORTS, timeout = 100, rollbackFor = PatientNullException.class)
public Patient addPatient(Patient patient) {
    if(patient != null) {
        return patientRepository.save(patient);
    } else {
        throw new PatientNullException("Patient object is null");
    }
}
```

| Attribute | Value | Explanation |
|-----------|-------|-------------|
| `propagation` | `SUPPORTS` | Join existing transaction if present, otherwise execute non-transactionally |
| `timeout` | `100` (seconds) | Transaction fails if exceeds 100 seconds |
| `rollbackFor` | `PatientNullException.class` | Rollback if this exception is thrown |

#### **Propagation Types**

| Type | Behavior | Use Case |
|------|----------|----------|
| `REQUIRED` (default) | Join existing or create new transaction | Most common |
| `REQUIRES_NEW` | Always create new transaction | Independent operations |
| `SUPPORTS` | Join if exists, else no transaction | Read operations |
| `NOT_SUPPORTED` | Execute non-transactionally | Avoid overhead for simple queries |
| `MANDATORY` | Must have existing transaction | Enforce transactional context |
| `NEVER` | Fail if transaction exists | Non-transactional only |
| `NESTED` | Create savepoint within transaction | Partial rollback scenarios |

#### **Rollback Rules**

```java
@Transactional(rollbackFor = PatientNotFoundException.class)
public Patient updatePatient(String adhaarCardNo, long phoneNumber, String email) {
    if(patientRepository.existsById(adhaarCardNo) && email != null && phoneNumber != 0) {
        Patient patient = patientRepository.findById(adhaarCardNo).get();
        patient.setContactNumber(phoneNumber);
        patient.setEmail(email);
        return patientRepository.save(patient);
    } else {
        throw new PatientNotFoundException("Patient not found to update");
    }
}
```

| Rule | Default Behavior | Custom Behavior |
|------|------------------|-----------------|
| Checked exceptions | No rollback | Use `rollbackFor` to rollback |
| Runtime exceptions | Rollback automatically | Always rollback |
| `rollbackFor` | Specify exceptions to rollback | `rollbackFor = {Exception1.class, Exception2.class}` |
| `noRollbackFor` | Specify exceptions to NOT rollback | `noRollbackFor = OptimisticLockException.class` |

---

### **JPA Criteria API (Dynamic Queries)**

```java
public List<Patient> getPatientByPhoneNumber(long contactNumber) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
    Root<Patient> patient = cq.from(Patient.class);
    cq.select(patient).where(cb.equal(patient.get("contactNumber"), contactNumber));
    return entityManager.createQuery(cq).getResultList();
}
```

**Why Criteria API?**
- Phone number is **not unique** → multiple results possible
- Type-safe queries (compile-time checking)
- Dynamic query construction

**Criteria API Components:**

| Component | Purpose | Example |
|-----------|---------|---------|
| `CriteriaBuilder` | Factory for query parts | Create predicates, expressions |
| `CriteriaQuery<T>` | Query definition | Defines SELECT, FROM, WHERE |
| `Root<T>` | FROM clause | Represents the entity being queried |
| `Predicate` | WHERE conditions | `cb.equal()`, `cb.like()`, etc. |

**Criteria API vs. Query Methods:**

| Aspect | Query Methods | Criteria API |
|--------|---------------|--------------|
| Syntax | Method names | Java code |
| Type Safety | ✅ Compile-time | ✅ Compile-time |
| Dynamic Queries | ❌ Static | ✅ Dynamic |
| Complexity | Simple | Complex queries |
| Readability | High | Moderate |

**Example Comparison:**

```java
// Query Method (simple, static)
List<Patient> findByContactNumber(long number);

// Criteria API (dynamic, complex)
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
Root<Patient> patient = cq.from(Patient.class);
// Can add conditions dynamically based on parameters
if (number != null) {
    cq.where(cb.equal(patient.get("contactNumber"), number));
}
```

---

### **EntityManager**

```java
@Autowired
private EntityManager entityManager;
```

**What is EntityManager?**
- JPA interface for interacting with persistence context
- Manages entity lifecycle (persist, merge, remove, find)
- Required for Criteria API and native queries

**Common Operations:**

| Method | Purpose | Example |
|--------|---------|---------|
| `persist(entity)` | Insert new entity | `em.persist(patient)` |
| `merge(entity)` | Update existing entity | `em.merge(patient)` |
| `remove(entity)` | Delete entity | `em.remove(patient)` |
| `find(class, id)` | Find by primary key | `em.find(Patient.class, "123456789012")` |
| `createQuery(...)` | Create JPQL/Criteria query | Used in Criteria API |

---

## ⚠️ Exception Handling

### **Custom Exception Hierarchy**

```
RuntimeException
├── PatientNullException
├── PatientNotFoundException
├── DoctorNullException
├── DoctorNotFoundException
├── AddressNullException
├── AddressNotFoundException
└── PersonNotFoundException
```

#### **Custom Exception Example**

```java
public class PatientNotFoundException extends RuntimeException implements Serializable {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
```

**Why `RuntimeException`?**

| Exception Type | Checked/Unchecked | Must Declare | Best For |
|----------------|-------------------|--------------|----------|
| `Exception` | Checked | Yes (`throws` required) | Recoverable errors |
| `RuntimeException` ⭐ | Unchecked | No | Programming errors, not found scenarios |

**Why `Serializable`?**
- Allows exception to be sent over network (in distributed systems)
- Required for remote method invocation (RMI)
- Best practice for exceptions

---

### **Global Exception Handler**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(PatientNullException.class)
    public ResponseEntity<GenericMessage<String>> handlePatientNullException(PatientNullException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericMessage<>(ex.getMessage()));
    }
    
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<GenericMessage<String>> handlePatientNotFoundException(PatientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new GenericMessage<>(ex.getMessage()));
    }
    
    // More handlers...
}
```

#### **Exception Handling Annotations**

| Annotation | Purpose | Scope |
|------------|---------|-------|
| `@ControllerAdvice` | Global exception handler | All controllers |
| `@ExceptionHandler(Exception.class)` | Handles specific exception type | Method-level |

**How It Works:**
1. Exception thrown in controller/service
2. Spring checks for matching `@ExceptionHandler`
3. Method executes and returns ResponseEntity
4. Client receives formatted error response

#### **HTTP Status Codes Used**

| Status Code | Meaning | Use Case |
|-------------|---------|----------|
| `400 BAD_REQUEST` | Invalid input | Null objects, validation errors |
| `404 NOT_FOUND` | Resource doesn't exist | Entity not found in database |
| `500 INTERNAL_SERVER_ERROR` | Server error | Unexpected exceptions |

---

### **Bean Validation Exception Handler**

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<GenericMessage<String>> handleArgumentException(MethodArgumentNotValidException ex) {
    Map<String, String> response = new HashMap<>();
    Map<String, String> errors = new HashMap<>();
    
    ex.getBindingResult().getFieldErrors().forEach(error -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });
    
    response.put("Validation Errors", errors.toString());
    response.put("Total Errors", String.valueOf(errors.size()));
    response.put("TimeStamp", LocalDateTime.now().toString());
    
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new GenericMessage<>(response.toString()));
}
```

**What is `MethodArgumentNotValidException`?**
- Thrown when `@Valid` fails on request body
- Contains all validation errors from Bean Validation

**Example Error Response:**

```json
{
  "message": "{Validation Errors={firstName=First name must contain only alphabetic characters, email=Invalid email format}, Total Errors=2, TimeStamp=2025-02-11T10:30:00}"
}
```

---

## 📝 DTOs & Mappers

### **Why DTOs?**

| Aspect | Entity (Model) | DTO |
|--------|----------------|-----|
| Purpose | Database representation | API contract |
| Exposure | Internal only | External (client-facing) |
| Fields | All database columns | Only needed for API |
| Validation | Database constraints | API validation rules |
| Coupling | Tightly coupled to DB | Decoupled from DB |

**Benefits:**
1. **Security**: Don't expose sensitive fields (e.g., internal IDs, audit fields)
2. **Flexibility**: API can change without altering database
3. **Validation**: Different validation rules for input vs. database
4. **Performance**: Send only necessary data

---

### **DTO Hierarchy**

```
PersonDTO (Abstract)
├── PatientDTO
└── DoctorDTO
```

#### **PersonDTO (Abstract)**

```java
@SuperBuilder
public abstract class PersonDTO implements Serializable {
    private FullName fullName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private long contactNumber;
    
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", 
             message = "Invalid email format")
    private String email;
}
```

**Why Abstract?**
- `PersonDTO` is never used directly
- Forces use of concrete types (`PatientDTO`, `DoctorDTO`)
- Shares common validation logic

---

### **Bean Validation Annotations**

```java
public class FullName {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabetic characters")
    private String firstName;
    
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only alphabetic characters")
    private String lastName;
}
```

#### **Validation Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@NotNull` | Field cannot be null | Required fields |
| `@NotEmpty` | String cannot be empty | Non-blank text |
| `@NotBlank` | Not null, not empty, not whitespace | Trimmed required fields |
| `@Pattern(regexp)` | Must match regex | Email, phone, names |
| `@Size(min, max)` | Length constraints | `@Size(min=2, max=50)` |
| `@Min(value)` | Minimum numeric value | `@Min(18)` for age |
| `@Max(value)` | Maximum numeric value | `@Max(120)` for age |
| `@Email` | Valid email format | Email fields |
| `@Past` | Date in the past | Date of birth |
| `@Future` | Date in the future | Appointment dates |
| `@Positive` | Positive number | Quantities |
| `@Valid` | Cascade validation to nested objects | `@Valid FullName fullName` |

**Regex Pattern Examples:**

| Pattern | Validates | Example |
|---------|-----------|---------|
| `^[A-Za-z]+$` | Only alphabetic characters | Names |
| `^[0-9]{10}$` | Exactly 10 digits | Phone numbers |
| `^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$` | Email format | user@example.com |
| `^[0-9]{6}$` | 6-digit PIN code | 600001 |

---

### **Response DTOs (Records)**

```java
public record PatientResponse(
    String adhaarCardNo,
    FullNameResponse fullNameResponse,
    String email,
    Gender gender,
    LocalDate dob,
    long contactNo,
    String ailment,
    String occupation
) {}
```

**Why Java Records?**

| Feature | Traditional Class | Record |
|---------|-------------------|--------|
| Immutability | Manual (`final` fields) | Automatic |
| Constructor | Manual | Auto-generated |
| Getters | Manual | Auto-generated (no `get` prefix) |
| `equals()`/`hashCode()` | Manual or Lombok | Auto-generated |
| `toString()` | Manual or Lombok | Auto-generated |
| Boilerplate | High | Minimal |

**Records are ideal for:**
- DTOs (immutable data transfer)
- API responses
- Value objects
- Configuration holders

---

### **MapStruct Mappers**

#### **Why MapStruct?**

| Approach | Pros | Cons |
|----------|------|------|
| Manual mapping | Full control | Boilerplate, error-prone |
| `BeanUtils.copyProperties()` | Simple | Runtime errors, no compile-time checking |
| **MapStruct** ⭐ | Compile-time generation, type-safe, fast | Requires annotation processing |

#### **Basic Mapper**

```java
@Mapper(componentModel = "spring", uses = {FullNameMapper.class})
public interface PatientMapper {
    @Mapping(source = "fullName", target = "fullNameResponse")
    @Mapping(source = "dateOfBirth", target = "dob")
    @Mapping(source = "contactNumber", target = "contactNo")
    PatientResponse toDTOs(Patient patient);
    
    List<PatientResponse> toDTOs(List<Patient> patients);
}
```

#### **MapStruct Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Mapper` | Marks interface as MapStruct mapper | All mappers |
| `componentModel = "spring"` | Generate Spring bean | Allows `@Autowired` |
| `uses = {OtherMapper.class}` | Use other mappers for nested objects | Reuse FullNameMapper |
| `@Mapping` | Custom field mapping | Different source/target names |
| `source` | Source field name | Entity field |
| `target` | Target field name | DTO field |

#### **How MapStruct Works**

1. **Compile Time:**
   ```java
   // You write:
   @Mapper(componentModel = "spring")
   public interface PatientMapper {
       PatientResponse toDTOs(Patient patient);
   }
   ```

2. **MapStruct Generates:**
   ```java
   @Component
   public class PatientMapperImpl implements PatientMapper {
       @Autowired
       private FullNameMapper fullNameMapper;
       
       @Override
       public PatientResponse toDTOs(Patient patient) {
           if (patient == null) {
               return null;
           }
           
           PatientResponse response = new PatientResponse(
               patient.getAdhaarCardNo(),
               fullNameMapper.toDTOs(patient.getFullName()),
               patient.getEmail(),
               patient.getGender(),
               patient.getDateOfBirth(),
               patient.getContactNumber(),
               patient.getAilment(),
               patient.getOccupation()
           );
           
           return response;
       }
   }
   ```

**Advantages:**
- ✅ No reflection (fast)
- ✅ Compile-time errors for missing fields
- ✅ Type-safe
- ✅ Supports complex mappings

---

### **Nested Mappers**

```java
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface AddressMapper {
    AddressResponse entitytodto(Address address);
}
```

**How Nested Mapping Works:**
1. `AddressMapper` needs to convert `Address` → `AddressResponse`
2. `AddressResponse` contains `PersonResponse`
3. `uses = {PersonMapper.class}` tells MapStruct to use `PersonMapper` for `Person` → `PersonResponse`
4. MapStruct auto-wires `PersonMapper` into `AddressMapperImpl`

---

### **GenericMessage Wrapper**

```java
@Data
@NoArgsConstructor
public class GenericMessage<T> {
    private T object;
    private String message;
    
    public GenericMessage(T object) {
        this.object = object;
    }
    
    public GenericMessage(String message) {
        this.message = message;
    }
}
```

**Why Generic Wrapper?**
- Consistent API response format
- Can wrap success data OR error messages
- Type-safe with generics

**Usage Examples:**

```java
// Success response with data
return new GenericMessage<>(patientResponse);
// {"object": {...}, "message": null}

// Error response with message
return new GenericMessage<>("Patient not found");
// {"object": null, "message": "Patient not found"}
```

---

## 🎮 Controllers (REST APIs)

### **REST Controller Annotations**

```java
@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private PatientMapper patientMapper;
    
    // Endpoints...
}
```

| Annotation | Purpose | Effect |
|------------|---------|--------|
| `@RestController` | Marks class as REST controller | Combines `@Controller` + `@ResponseBody` |
| `@RequestMapping("/path")` | Base URL for all endpoints | All methods prefixed with `/patients` |
| `@Autowired` | Dependency injection | Spring injects service/mapper instances |

**@RestController vs @Controller:**

| Annotation | Return Type | Use Case |
|------------|-------------|----------|
| `@Controller` | View name (HTML) | Traditional web apps (Thymeleaf, JSP) |
| `@RestController` | JSON/XML data | RESTful APIs |

---

### **HTTP Method Annotations**

| Annotation | HTTP Method | Purpose | Idempotent? |
|------------|-------------|---------|-------------|
| `@GetMapping` | GET | Retrieve data | ✅ Yes |
| `@PostMapping` | POST | Create new resource | ❌ No |
| `@PutMapping` | PUT | Replace entire resource | ✅ Yes |
| `@PatchMapping` | PATCH | Partial update | ❌ No |
| `@DeleteMapping` | DELETE | Delete resource | ✅ Yes |

**Idempotent:** Multiple identical requests have same effect as single request

---

### **Request Parameter Annotations**

#### **@RequestBody**

```java
@PostMapping("/v1.0")
public ResponseEntity<GenericMessage<String>> addPatient(@Valid @RequestBody PatientDTO patientDTO) {
    // patientDTO populated from JSON request body
}
```

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@RequestBody` | Bind HTTP request body to object | JSON → Java object |
| `@Valid` | Trigger Bean Validation | Validates fields before method executes |

**Request Example:**
```json
POST /patients/v1.0
Content-Type: application/json

{
  "fullName": {
    "firstName": "John",
    "lastName": "Doe"
  },
  "email": "john@example.com",
  "gender": "MALE",
  "dateOfBirth": "1990-01-01",
  "contactNumber": 9876543210,
  "ailment": "Fever",
  "occupation": "Engineer"
}
```

---

#### **@PathParam vs @PathVariable**

```java
// Using @PathParam (from Jakarta WebSocket, works in Spring)
@GetMapping("/v1.0/{adhaarCardNo}")
public ResponseEntity<...> getPatient(@PathParam("adhaarCardNo") String adhaarCardNo) {
    // adhaarCardNo = "123456789012"
}

// Using @PathVariable (Spring's native annotation)
@GetMapping("/v1.0/{adhaarCardNo}")
public ResponseEntity<...> getPatient(@PathVariable String adhaarCardNo) {
    // adhaarCardNo = "123456789012"
}
```

**URL:** `GET /patients/v1.0/123456789012`

| Annotation | Package | Recommendation |
|------------|---------|----------------|
| `@PathParam` | `jakarta.websocket` | Use `@PathVariable` instead |
| `@PathVariable` ⭐ | `org.springframework.web.bind.annotation` | Spring's standard |

---

#### **@RequestParam**

```java
@PatchMapping("/v1.0")
public ResponseEntity<...> updatePatient(
    @RequestParam String adhaarCardNo,
    @RequestParam long contactNo,
    @RequestParam String email
) {
    // Extracts from query string
}
```

**URL:** `PATCH /patients/v1.0?adhaarCardNo=123456789012&contactNo=9876543210&email=test@example.com`

**@RequestParam Attributes:**

| Attribute | Default | Purpose | Example |
|-----------|---------|---------|---------|
| `required` | `true` | Parameter mandatory | `@RequestParam(required = false)` |
| `defaultValue` | none | Default if missing | `@RequestParam(defaultValue = "10")` |
| `name` | method parameter name | Custom parameter name | `@RequestParam(name = "id")` |

---

### **Security with @PreAuthorize**

```java
@PreAuthorize("hasAnyAuthority('SCOPE_developer')")
@PostMapping("/v1.0")
public ResponseEntity<...> addPatient(@Valid @RequestBody PatientDTO patientDTO) {
    // Only users with 'developer' scope can access
}

@PreAuthorize("hasAnyAuthority('SCOPE_tester', 'SCOPE_developer')")
@GetMapping("/v1.0")
public List<PatientResponse> getAllPatients() {
    // Tester OR developer can access
}
```

#### **@PreAuthorize Expressions**

| Expression | Meaning | Example |
|------------|---------|---------|
| `hasRole('ROLE_ADMIN')` | User has role | Spring Security roles |
| `hasAuthority('SCOPE_developer')` | User has authority | OAuth2 scopes |
| `hasAnyAuthority('A', 'B')` | User has any of authorities | Multiple scopes |
| `hasAnyRole('ADMIN', 'USER')` | User has any of roles | Multiple roles |
| `permitAll()` | Allow everyone | Public endpoints |
| `denyAll()` | Deny everyone | Disabled endpoints |
| `isAuthenticated()` | User is logged in | Any authenticated user |
| `#param == authentication.name` | SpEL expression | Custom logic |

**Role vs Authority:**
- **Role:** High-level (ADMIN, USER, MANAGER)
- **Authority:** Fine-grained (SCOPE_developer, SCOPE_tester)

**OAuth2 Scopes as Authorities:**
- Keycloak scopes become `SCOPE_` prefixed authorities
- Scope `developer` → Authority `SCOPE_developer`

---

### **ResponseEntity**

```java
return ResponseEntity.status(HttpStatus.CREATED)
        .body(new GenericMessage<>(patientResponse));
```

**Why ResponseEntity?**
- Full control over HTTP response
- Set status code, headers, body

#### **ResponseEntity Builder Methods**

| Method | Purpose | Example |
|--------|---------|---------|
| `.status(HttpStatus)` | Set status code | `.status(HttpStatus.OK)` |
| `.body(object)` | Set response body | `.body(patientResponse)` |
| `.header(name, value)` | Add custom header | `.header("X-Custom", "value")` |
| `.ok(body)` | 200 OK with body | `ResponseEntity.ok(data)` |
| `.created(uri)` | 201 Created with Location header | `.created(URI.create("/patients/123"))` |
| `.noContent()` | 204 No Content | `ResponseEntity.noContent().build()` |
| `.notFound()` | 404 Not Found | `ResponseEntity.notFound().build()` |

---

### **Complete Endpoint Examples**

#### **POST - Create Patient**

```java
@PreAuthorize("hasAnyAuthority('SCOPE_developer')")
@PostMapping("/v1.0")
public ResponseEntity<GenericMessage<String>> addPatient(@Valid @RequestBody PatientDTO patientDTO) {
    // 1. Build FullName from DTO
    FullName fullName = FullName.builder()
        .firstName(patientDTO.getFullName().getFirstName())
        .lastName(patientDTO.getFullName().getLastName())
        .build();
    
    // 2. Build Patient entity
    Patient patient = Patient.builder()
        .fullName(fullName)
        .dateOfBirth(patientDTO.getDateOfBirth())
        .email(patientDTO.getEmail())
        .contactNumber(patientDTO.getContactNumber())
        .gender(patientDTO.getGender())
        .ailment(patientDTO.getAilment())
        .occupation(patientDTO.getOccupation())
        .build();
    
    // 3. Save to database
    Patient savedPatient = patientService.addPatient(patient);
    
    // 4. Convert to response DTO
    PatientResponse patientResponse = patientMapper.toDTOs(savedPatient);
    
    // 5. Return response
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new GenericMessage<>(patientResponse));
}
```

**Flow:**
1. **Validation:** `@Valid` triggers Bean Validation on `PatientDTO`
2. **Authorization:** `@PreAuthorize` checks for `developer` scope
3. **Mapping:** Manual DTO → Entity conversion (could use MapStruct)
4. **Business Logic:** Service layer handles database operation
5. **Response Mapping:** Entity → Response DTO via MapStruct
6. **HTTP Response:** 201 Created with patient data

---

#### **GET - Retrieve All Patients**

```java
@PreAuthorize("hasAnyAuthority('SCOPE_tester', 'SCOPE_developer')")
@GetMapping("/v1.0")
public List<PatientResponse> getAllPatients() {
    List<Patient> patients = patientService.getAllPatients();
    return patientMapper.toDTOs(patients);
}
```

**Why no ResponseEntity?**
- `@RestController` automatically wraps return value in HTTP 200 OK
- Simpler for straightforward GET requests
- Spring serializes List → JSON array

---

#### **GET - Retrieve by ID**

```java
@PreAuthorize("hasAnyAuthority('SCOPE_tester', 'SCOPE_developer')")
@GetMapping("/v1.0/{adhaarCardNo}")
public ResponseEntity<GenericMessage<String>> getPatientByAdhaarCardNo(
    @PathParam("adhaarCardNo") String adhaarCardNo
) {
    Patient patient = patientService.getPatientByAdhaarCardNo(adhaarCardNo);
    PatientResponse patientResponse = patientMapper.toDTOs(patient);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(new GenericMessage<>(patientResponse));
}
```

**Exception Handling:**
- If patient not found, service throws `PatientNotFoundException`
- Global exception handler catches it and returns 404

---

#### **PATCH - Partial Update**

```java
@PreAuthorize("hasAnyAuthority('SCOPE_developer')")
@PatchMapping("/v1.0")
public ResponseEntity<GenericMessage<String>> updatePatient(
    @RequestParam String adhaarCardNo,
    @RequestParam long contactNo,
    @RequestParam String email
) {
    Patient updatedPatient = patientService.updatePatient(adhaarCardNo, contactNo, email);
    PatientResponse patientResponse = patientMapper.toDTOs(updatedPatient);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(new GenericMessage<>(patientResponse));
}
```

**PATCH vs PUT:**

| Method | Updates | Example |
|--------|---------|---------|
| `PUT` | Replaces entire resource | Send all fields |
| `PATCH` ⭐ | Partial update | Send only changed fields |

---

#### **DELETE - Remove Resource**

```java
@PreAuthorize("hasAnyAuthority('SCOPE_developer')")
@DeleteMapping("/v1.0")
public ResponseEntity<GenericMessage<String>> deletePatient(
    @RequestParam String adhaarCardNo
) {
    boolean isDeleted = patientService.deletePatient(adhaarCardNo);
    
    if (isDeleted) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(new GenericMessage<>("Patient deleted successfully with Adhaar Card No: " + adhaarCardNo));
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new GenericMessage<>("Patient not found with Adhaar Card No: " + adhaarCardNo));
    }
}
```

**Delete Response Patterns:**

| Pattern | Status Code | Use Case |
|---------|-------------|----------|
| 204 No Content | 204 | Successful deletion, no body |
| 200 OK with message | 200 | Deletion confirmation message |
| 202 Accepted with message | 202 | Async deletion or confirmation |
| 404 Not Found | 404 | Resource doesn't exist |

---

## 🔐 Security Configuration

### **Spring Security Filter Chain**

```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
        
        return http.build();
    }
}
```

#### **Security Configuration Breakdown**

| Configuration | Purpose | Explanation |
|---------------|---------|-------------|
| `@EnableMethodSecurity` | Enable `@PreAuthorize` | Method-level security annotations |
| `.csrf().disable()` | Disable CSRF protection | Stateless REST APIs don't need CSRF |
| `.requestMatchers(...).permitAll()` | Public endpoints | Swagger UI, API docs accessible without auth |
| `.requestMatchers("/api/**").authenticated()` | Protected endpoints | Require authentication |
| `.oauth2ResourceServer().jwt()` | JWT validation | Validate tokens against Keycloak |

#### **CSRF (Cross-Site Request Forgery)**

| Aspect | Traditional Web Apps | REST APIs |
|--------|---------------------|-----------|
| State | Stateful (session cookies) | Stateless (JWT tokens) |
| CSRF Risk | High | Low (no cookies) |
| Protection | CSRF tokens required | Not needed |

**Why disable CSRF for REST APIs?**
- JWT tokens in `Authorization` header (not cookies)
- Browsers don't auto-send `Authorization` headers
- No CSRF vulnerability in stateless design

---

### **OAuth2 Resource Server**

```java
.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
```

**What is a Resource Server?**
- Server that hosts protected resources (your API)
- Validates access tokens from Authorization Server (Keycloak)
- Checks token signature, expiry, issuer, audience

**JWT Validation Process:**

```
1. Client sends request with JWT:
   Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...

2. Spring Security intercepts request

3. Fetches Keycloak's public key (from issuer-uri)

4. Validates JWT:
   ✅ Signature (using public key)
   ✅ Expiry (exp claim)
   ✅ Issuer (iss claim)
   ✅ Audience (aud claim)

5. If valid:
   - Extract scopes/authorities from JWT
   - Populate SecurityContext
   - Allow request to proceed

6. If invalid:
   - Return 401 Unauthorized
```

---

### **Keycloak Integration**

#### **application.properties**

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/realms/master
keycloak.issuer=http://localhost:8080/realms/master
keycloak.realm=master
```

**Keycloak Components:**

| Component | Description | Example |
|-----------|-------------|---------|
| Realm | Isolated group of users, roles, clients | `master` |
| Client | Application using Keycloak | `hospitalmangementapi` |
| User | Individual user account | `developer@example.com` |
| Role | User role | `developer`, `tester` |
| Scope | Fine-grained permission | `developer`, `tester` |

#### **JWT Token Structure**

```json
{
  "header": {
    "alg": "RS256",
    "typ": "JWT"
  },
  "payload": {
    "iss": "http://localhost:8080/realms/master",
    "sub": "user-id-123",
    "aud": "hospitalmangementapi",
    "exp": 1707649200,
    "iat": 1707645600,
    "scope": "developer tester"
  },
  "signature": "..."
}
```

| Claim | Purpose | Example |
|-------|---------|---------|
| `iss` (Issuer) | Token issuer | Keycloak realm URL |
| `sub` (Subject) | User identifier | User ID |
| `aud` (Audience) | Intended recipient | Your API client ID |
| `exp` (Expiry) | Token expiration | Unix timestamp |
| `iat` (Issued At) | Token creation time | Unix timestamp |
| `scope` | OAuth2 scopes | `developer tester` |

**Spring Security Scope Conversion:**
- Scope `developer` → Authority `SCOPE_developer`
- Used in `@PreAuthorize("hasAuthority('SCOPE_developer')")`

---

## 📖 API Documentation (Swagger/OpenAPI)

### **OpenAPI Configuration**

```java
@Configuration
public class OpenAPISecurityConfig {
    @Value("${keycloak.issuer}")
    String issuer;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("keycloakDeveloper", oauthScheme("developer", "Developer access"))
                .addSecuritySchemes("keycloakTester", oauthScheme("tester", "Tester access"))
            )
            .addSecurityItem(new SecurityRequirement().addList("keycloakDeveloper"))
            .addSecurityItem(new SecurityRequirement().addList("keycloakTester"))
            .info(new Info()
                .title("Customer Management Service")
                .description("Customer API with OAuth2 Security")
                .version("1.0"));
    }

    private SecurityScheme oauthScheme(String scope, String desc) {
        String authUrl = issuer + "/protocol/openid-connect/auth";
        String tokenUrl = issuer + "/protocol/openid-connect/token";

        OAuthFlow code = new OAuthFlow()
            .authorizationUrl(authUrl)
            .tokenUrl(tokenUrl)
            .scopes(new Scopes().addString(scope, desc));

        return new SecurityScheme()
            .type(SecurityScheme.Type.OAUTH2)
            .flows(new OAuthFlows().authorizationCode(code));
    }
}
```

#### **OpenAPI Components**

| Component | Purpose | Configuration |
|-----------|---------|---------------|
| `SecurityScheme` | Define auth method | OAuth2, API Key, Basic Auth |
| `OAuthFlow` | OAuth flow type | Authorization Code, Client Credentials |
| `Scopes` | OAuth scopes | `developer`, `tester` |
| `SecurityRequirement` | Apply security to operations | Endpoints require auth |
| `Info` | API metadata | Title, description, version |

#### **OAuth2 Flow (Authorization Code)**

```
1. User clicks "Authorize" in Swagger UI
2. Redirected to Keycloak login:
   http://localhost:8080/realms/master/protocol/openid-connect/auth
3. User logs in with credentials
4. Keycloak redirects back with authorization code
5. Swagger exchanges code for access token:
   POST http://localhost:8080/realms/master/protocol/openid-connect/token
6. Swagger stores token and includes in requests:
   Authorization: Bearer <token>
```

---

### **Swagger UI Usage**

**Access:** `http://localhost:7076/swagger-ui.html`

**Features:**
1. **Interactive Testing:** Execute API calls directly
2. **OAuth Integration:** Login with Keycloak
3. **Schema Documentation:** View request/response models
4. **Try It Out:** Test endpoints with sample data

**Authorizing in Swagger:**
1. Click "Authorize" button
2. Select scope (developer or tester)
3. Login with Keycloak credentials
4. Token auto-included in subsequent requests

---

## 📚 Annotations Reference

### **Spring Framework Annotations**

| Annotation | Package | Purpose | Usage |
|------------|---------|---------|-------|
| `@SpringBootApplication` | `org.springframework.boot.autoconfigure` | Main application class | Entry point |
| `@Configuration` | `org.springframework.context.annotation` | Configuration class | Define beans |
| `@Bean` | `org.springframework.context.annotation` | Bean definition | Method returns bean instance |
| `@Component` | `org.springframework.stereotype` | Generic component | Spring-managed class |
| `@Service` | `org.springframework.stereotype` | Service layer | Business logic |
| `@Repository` | `org.springframework.stereotype` | Data access layer | Database operations |
| `@RestController` | `org.springframework.web.bind.annotation` | REST controller | Combines `@Controller` + `@ResponseBody` |
| `@Controller` | `org.springframework.stereotype` | MVC controller | Traditional web apps |
| `@Autowired` | `org.springframework.beans.factory.annotation` | Dependency injection | Inject beans |
| `@Value` | `org.springframework.beans.factory.annotation` | Inject property value | Read from properties file |

### **Spring Web Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@RequestMapping` | Base URL mapping | `@RequestMapping("/api")` |
| `@GetMapping` | HTTP GET | `@GetMapping("/patients")` |
| `@PostMapping` | HTTP POST | `@PostMapping("/patients")` |
| `@PutMapping` | HTTP PUT | `@PutMapping("/patients/{id}")` |
| `@PatchMapping` | HTTP PATCH | `@PatchMapping("/patients/{id}")` |
| `@DeleteMapping` | HTTP DELETE | `@DeleteMapping("/patients/{id}")` |
| `@RequestBody` | Bind request body to parameter | `@RequestBody PatientDTO dto` |
| `@PathVariable` | Extract path variable | `@PathVariable String id` |
| `@RequestParam` | Extract query parameter | `@RequestParam String name` |
| `@ResponseStatus` | Set HTTP status code | `@ResponseStatus(HttpStatus.CREATED)` |

### **Spring Security Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@EnableMethodSecurity` | Enable method-level security | Class-level on SecurityConfig |
| `@PreAuthorize` | Authorization before method execution | `@PreAuthorize("hasRole('ADMIN')")` |
| `@PostAuthorize` | Authorization after method execution | Check returned data |
| `@Secured` | Role-based security | `@Secured("ROLE_ADMIN")` |
| `@RolesAllowed` | JSR-250 role check | `@RolesAllowed("ADMIN")` |

### **Spring Data JPA Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Entity` | JPA entity | Maps class to table |
| `@Table` | Specify table name | `@Table(name = "patients")` |
| `@Id` | Primary key | `@Id private String id` |
| `@GeneratedValue` | Auto-generated ID | `@GeneratedValue(strategy = AUTO)` |
| `@Column` | Column mapping | `@Column(name = "first_name", length = 50)` |
| `@Embedded` | Embed object as columns | `@Embedded private Address address` |
| `@Embeddable` | Embeddable class | Class that can be embedded |
| `@Enumerated` | Enum mapping | `@Enumerated(EnumType.STRING)` |
| `@Temporal` | Date/time mapping | `@Temporal(TemporalType.DATE)` |
| `@Lob` | Large object (BLOB/CLOB) | `@Lob private byte[] data` |
| `@Transient` | Exclude from persistence | `@Transient private String temp` |
| `@OneToOne` | One-to-one relationship | `@OneToOne private Address address` |
| `@OneToMany` | One-to-many relationship | `@OneToMany private List<Order> orders` |
| `@ManyToOne` | Many-to-one relationship | `@ManyToOne private Customer customer` |
| `@ManyToMany` | Many-to-many relationship | `@ManyToMany private List<Tag> tags` |
| `@JoinColumn` | Foreign key column | `@JoinColumn(name = "customer_id")` |
| `@JoinTable` | Join table for many-to-many | Specify table name and columns |
| `@Inheritance` | Inheritance strategy | `@Inheritance(strategy = JOINED)` |

### **Spring Transaction Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Transactional` | Transactional method/class | `@Transactional(readOnly = true)` |
| `propagation` | Transaction propagation | `propagation = REQUIRED` |
| `isolation` | Transaction isolation level | `isolation = READ_COMMITTED` |
| `timeout` | Transaction timeout (seconds) | `timeout = 30` |
| `readOnly` | Read-only optimization | `readOnly = true` |
| `rollbackFor` | Exceptions that trigger rollback | `rollbackFor = Exception.class` |
| `noRollbackFor` | Exceptions to ignore | `noRollbackFor = NotFoundException.class` |

### **Bean Validation Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Valid` | Trigger validation | `@Valid @RequestBody PatientDTO dto` |
| `@NotNull` | Cannot be null | `@NotNull private String name` |
| `@NotEmpty` | Cannot be empty (collection/string) | `@NotEmpty private String name` |
| `@NotBlank` | Cannot be null, empty, or whitespace | `@NotBlank private String name` |
| `@Size` | Size constraint | `@Size(min = 2, max = 50)` |
| `@Min` | Minimum value | `@Min(18)` |
| `@Max` | Maximum value | `@Max(120)` |
| `@Pattern` | Regex pattern | `@Pattern(regexp = "^[A-Za-z]+$")` |
| `@Email` | Email format | `@Email private String email` |
| `@Past` | Date in the past | `@Past private LocalDate dob` |
| `@Future` | Date in the future | `@Future private LocalDate appointment` |
| `@Positive` | Positive number | `@Positive private int age` |
| `@Negative` | Negative number | `@Negative private int debt` |
| `@PositiveOrZero` | Positive or zero | `@PositiveOrZero private int count` |

### **Lombok Annotations**

| Annotation | Purpose | Generates |
|------------|---------|-----------|
| `@Data` | All-in-one | `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode`, `@RequiredArgsConstructor` |
| `@Getter` | Getter methods | `getName()`, `getEmail()`, etc. |
| `@Setter` | Setter methods | `setName()`, `setEmail()`, etc. |
| `@ToString` | `toString()` method | String representation |
| `@EqualsAndHashCode` | `equals()` and `hashCode()` | Object comparison |
| `@NoArgsConstructor` | No-argument constructor | `new Patient()` |
| `@AllArgsConstructor` | All-args constructor | `new Patient(name, email, ...)` |
| `@RequiredArgsConstructor` | Constructor for `final` fields | Required dependencies |
| `@Builder` | Builder pattern | `Patient.builder().name("John").build()` |
| `@SuperBuilder` | Builder with inheritance | Extends parent builder |

### **MapStruct Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Mapper` | MapStruct mapper | `@Mapper(componentModel = "spring")` |
| `@Mapping` | Field mapping | `@Mapping(source = "fullName", target = "name")` |
| `@Mappings` | Multiple mappings | `@Mappings({@Mapping(...), @Mapping(...)})` |
| `@InheritInverseConfiguration` | Inverse mapping | Reverse source/target |
| `@BeanMapping` | Bean-level config | Null value strategy |

### **Swagger/OpenAPI Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Schema` | Document model | `@Schema(description = "Patient details")` |
| `@Operation` | Document operation | `@Operation(summary = "Get patient")` |
| `@Parameter` | Document parameter | `@Parameter(description = "Patient ID")` |
| `@ApiResponse` | Document response | `@ApiResponse(responseCode = "200")` |
| `@Hidden` | Hide from docs | `@Hidden private String internal` |

### **Eureka Annotations**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@EnableDiscoveryClient` | Enable Eureka client | Main application class |
| `@EnableEurekaClient` | Enable Eureka (Netflix-specific) | Same as above |

---

## 🎨 Design Patterns Used

| Pattern | Implementation | Benefit |
|---------|----------------|---------|
| **Repository Pattern** | Spring Data JPA repositories | Data access abstraction |
| **Service Layer Pattern** | `@Service` classes | Business logic separation |
| **DTO Pattern** | Separate DTOs from entities | Decouple API from database |
| **Builder Pattern** | Lombok `@Builder`, `@SuperBuilder` | Fluent object construction |
| **Dependency Injection** | `@Autowired`, constructor injection | Loose coupling |
| **Strategy Pattern** | Custom ID generation | Pluggable ID generation logic |
| **Template Method Pattern** | JPA inheritance | Shared behavior in base class |
| **Singleton Pattern** | Spring beans (default scope) | Single instance per application |
| **Proxy Pattern** | Spring AOP, `@Transactional` | Cross-cutting concerns |
| **Factory Pattern** | MapStruct mappers | Object creation abstraction |

---

## 🚀 Running the Application

### **Prerequisites**

1. **Java 17+**
   ```bash
   java -version
   # Output: openjdk version "17.0.1"
   ```

2. **MySQL 8**
   ```bash
   mysql -u root -p
   CREATE DATABASE hospital_management_db;
   ```

3. **Keycloak Server**
   - Download: https://www.keycloak.org/downloads
   - Start: `bin/kc.sh start-dev` (Linux/Mac) or `bin\kc.bat start-dev` (Windows)
   - Access: http://localhost:8080
   - Create realm: `master`
   - Create client: `hospitalmangementapi`
   - Create users with scopes: `developer`, `tester`

4. **Eureka Server**
   - Clone Spring Cloud Eureka server project
   - Start on port 8761
   - Access: http://localhost:8761

### **Configuration Steps**

1. **Update application.properties:**
   ```properties
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```

2. **Build the project:**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

### **Access Points**

| Service | URL | Description |
|---------|-----|-------------|
| API | http://localhost:7076 | Main application |
| Swagger UI | http://localhost:7076/swagger-ui.html | API documentation |
| OpenAPI JSON | http://localhost:7076/v3/api-docs | OpenAPI spec |
| Eureka Dashboard | http://localhost:8761 | Service registry |
| Keycloak | http://localhost:8080 | Authentication server |

### **Testing the API**

1. **Get Access Token (using Postman):**
   ```
   POST http://localhost:8080/realms/master/protocol/openid-connect/token
   Content-Type: application/x-www-form-urlencoded

   grant_type=password
   client_id=hospitalmangementapi
   username=developer@example.com
   password=password
   scope=developer
   ```

2. **Call Protected Endpoint:**
   ```
   POST http://localhost:7076/patients/v1.0
   Authorization: Bearer <access_token>
   Content-Type: application/json

   {
     "fullName": {
       "firstName": "John",
       "lastName": "Doe"
     },
     ...
   }
   ```

---

## 📝 Sample API Requests

### **Create Patient**

```bash
curl -X POST "http://localhost:7076/patients/v1.0" \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": {
      "firstName": "John",
      "lastName": "Doe"
    },
    "gender": "MALE",
    "dateOfBirth": "1990-01-01",
    "contactNumber": 9876543210,
    "email": "john.doe@example.com",
    "ailment": "Fever",
    "occupation": "Engineer"
  }'
```

### **Get All Patients**

```bash
curl -X GET "http://localhost:7076/patients/v1.0" \
  -H "Authorization: Bearer <token>"
```

### **Add Address**

```bash
curl -X POST "http://localhost:7076/addresses/v1.0/123456789012" \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "houseNumber": "123",
    "streetName": "Main Street",
    "city": "Chennai",
    "state": "Tamil Nadu",
    "zipCode": "600001"
  }'
```

---

## 🔍 Database Schema

```sql
-- Person table (base)
CREATE TABLE person (
    adhaar_card_no VARCHAR(12) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    date_of_birth DATE NOT NULL,
    contact_number BIGINT,
    email VARCHAR(150) UNIQUE
);

-- Patient table (extends Person)
CREATE TABLE patient (
    adhaar_card_no VARCHAR(12) PRIMARY KEY,
    ailment VARCHAR(100) NOT NULL,
    occupation VARCHAR(100),
    FOREIGN KEY (adhaar_card_no) REFERENCES person(adhaar_card_no)
);

-- Doctor table (extends Person)
CREATE TABLE doctor (
    adhaar_card_no VARCHAR(12) PRIMARY KEY,
    license_number VARCHAR(50) UNIQUE NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    qualification VARCHAR(100) NOT NULL,
    FOREIGN KEY (adhaar_card_no) REFERENCES person(adhaar_card_no)
);

-- Address table
CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    house_number VARCHAR(10) NOT NULL,
    street_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    person_adhaar_card_no VARCHAR(12) NOT NULL,
    FOREIGN KEY (person_adhaar_card_no) REFERENCES person(adhaar_card_no)
);
```

---

## 🚧 TODO / Future Enhancements

- [ ] **Complete Doctor Module**
  - [ ] Implement `DoctorServiceImpl`
  - [ ] Create `DoctorController` with CRUD endpoints
  - [ ] Add doctor-specific business logic

- [ ] **Testing**
  - [ ] Unit tests (JUnit 5, Mockito)
  - [ ] Integration tests (TestContainers for MySQL)
  - [ ] API tests (RestAssured, MockMvc)
  - [ ] Test coverage > 80%

- [ ] **Pagination & Sorting**
  - [ ] Add `Pageable` parameter to list endpoints
  - [ ] Implement `PagedModel` responses
  - [ ] Add sorting support

- [ ] **Search & Filtering**
  - [ ] Advanced search with multiple criteria
  - [ ] Filtering by date ranges, gender, ailment
  - [ ] Full-text search (optional: Elasticsearch)

- [ ] **Appointment Management**
  - [ ] Create `Appointment` entity
  - [ ] Link patients with doctors
  - [ ] Add scheduling logic

- [ ] **Audit Logging**
  - [ ] Add `createdAt`, `updatedAt` fields
  - [ ] Track created by/updated by user
  - [ ] Use JPA auditing (`@CreatedDate`, `@LastModifiedDate`)

- [ ] **Dockerization**
  - [ ] Create `Dockerfile`
  - [ ] `docker-compose.yml` with MySQL, Keycloak, Eureka
  - [ ] Multi-stage builds for optimization

- [ ] **CI/CD Pipeline**
  - [ ] GitHub Actions / Jenkins pipeline
  - [ ] Automated testing
  - [ ] Deployment to cloud (AWS, Azure, GCP)

- [ ] **API Versioning**
  - [ ] Implement versioning strategy (URL, header, media type)
  - [ ] Deprecation policy

- [ ] **Rate Limiting**
  - [ ] Add rate limiting (Bucket4j, Redis)
  - [ ] Protect against abuse

- [ ] **Monitoring & Observability**
  - [ ] Spring Boot Actuator
  - [ ] Prometheus metrics
  - [ ] Grafana dashboards
  - [ ] ELK stack for logging

---

## 📚 Learning Resources

### **Official Documentation**
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Security](https://docs.spring.io/spring-security/reference/)
- [MapStruct](https://mapstruct.org/documentation/stable/reference/html/)
- [Lombok](https://projectlombok.org/features/all)
- [Keycloak](https://www.keycloak.org/documentation)

### **Books**
- *Spring in Action* by Craig Walls
- *Pro Spring Boot 2* by Felipe Gutierrez
- *Java Persistence with Hibernate* by Christian Bauer

### **Concepts to Study Further**
- JPA Entity Lifecycle
- Transaction Isolation Levels
- OAuth2/OpenID Connect
- Microservices Architecture
- RESTful API Design Principles
- Domain-Driven Design (DDD)

---

## 👤 Author

**Kunal Pal**  
Backend Developer | Microservices Enthusiast

📧 kunal.cs.dev@outlook.com  
🔗 [LinkedIn](https://www.linkedin.com/in/kunal70616c/)  
🐙 [GitHub](https://github.com/Kunal70616c)

---

## 📄 License

This project is created for educational purposes as a comprehensive learning reference for Spring Boot microservices development.

---

**Built with ☕ and precision - bugs handled quietly, efficiently, and without mercy.**

*Last Updated: February 11, 2026*
