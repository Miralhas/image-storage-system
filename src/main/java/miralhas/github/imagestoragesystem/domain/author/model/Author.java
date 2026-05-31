package miralhas.github.imagestoragesystem.domain.author.model;

import jakarta.persistence.*;
import lombok.*;
import miralhas.github.imagestoragesystem.domain.image.contract.HasImage;
import miralhas.github.imagestoragesystem.domain.image.model.Image;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static miralhas.github.imagestoragesystem.ImageStorageSystemApplication.SLG;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Author implements HasImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String slug;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	private OffsetDateTime dateOfBirth;

	@CreationTimestamp
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	private OffsetDateTime updatedAt;

	@JoinColumn(name = "author_id")
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Image> images = new ArrayList<>();

	public Path getImageRelativePath() {
		return Path.of(this.getClass().getSimpleName(), "%d-%s".formatted(id, name));
	}

	public void generateSlug() {
		this.slug = SLG.slugify(this.name);
	}

	public void addImages(List<Image> images) {
		this.images.addAll(images);
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Author author = (Author) o;
		return getId() != null && Objects.equals(getId(), author.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
