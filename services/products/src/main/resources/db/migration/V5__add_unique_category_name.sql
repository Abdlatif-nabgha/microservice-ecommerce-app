DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM category WHERE name IS NULL OR btrim(name) = '') THEN
        RAISE EXCEPTION 'Cannot require category names: category rows contain null or blank names';
    END IF;

    IF EXISTS (SELECT name FROM category GROUP BY name HAVING COUNT(*) > 1) THEN
        RAISE EXCEPTION 'Cannot make category names unique: duplicate names exist';
    END IF;
END;
$$;

ALTER TABLE category
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE category
    ADD CONSTRAINT uk_category_name UNIQUE (name);
